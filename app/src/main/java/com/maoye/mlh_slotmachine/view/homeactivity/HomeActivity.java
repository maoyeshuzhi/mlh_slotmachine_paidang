package com.maoye.mlh_slotmachine.view.homeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.apkmanager.DownLoadApk;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.VersionInfoBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.MD5;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.VersionManagerUtil;
import com.maoye.mlh_slotmachine.util.httputil.cache.CacheUtil;
import com.maoye.mlh_slotmachine.view.cartactivity.CartActivity;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.view.h5activity.H5Activity;
import com.maoye.mlh_slotmachine.view.print_select_activity.PrintSelectActivity;
import com.maoye.mlh_slotmachine.view.printreceiptactivity.PrintReceiptActivity;
import com.maoye.mlh_slotmachine.view.quickpayactivity.QuickpayActivity;
import com.maoye.mlh_slotmachine.webservice.URL;
import com.maoye.mlh_slotmachine.widget.BadgeView;
import com.maoye.mlh_slotmachine.widget.banner.Banner;
import com.maoye.mlh_slotmachine.widget.banner.BannerConfig;
import com.maoye.mlh_slotmachine.widget.banner.ViewBanner;
import com.maoye.mlh_slotmachine.widget.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends MVPBaseActivity<HomeContract.View, HomePresenter> implements HomeContract.View {

    @BindView(R.id.print_bill_bt)
    TextView printBillBt;
    @BindView(R.id.leftpage_img)
    ImageView leftpageImg;
    @BindView(R.id.rightpage_img)
    ImageView rightpageImg;

    @BindView(R.id.cart_img)
    ImageView cartImg;
    @BindView(R.id.print_ll)
    LinearLayout printLl;
    @BindView(R.id.head_banner)
    Banner headBanner;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.banner)
    ViewBanner banner;
    private BadgeView goodsNumView;
    private List<List<HomeBean.ListBeanX>> goodList = new ArrayList<>();
    private List<HomeBean.AdvertBean> advertBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        String s = MD5.MD5("M100118051115198227600.01maoye_mlhj4745");
        LogUtils.e("当前设备设备号："+ DeviceInfoUtil.getDeviceId());
        goodsNumView = new BadgeView(HomeActivity.this);
        goodsNumView.setStyle(1);
        goodsNumView.setTargetView(cartImg);
        goodsNumView.setBadgeMargin(10, 0, 0, 10);
        headBanner.setImageLoader(new GlideImageLoader());
        banner.setViewLoader(new HomeGoodsVH());
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.start();
        headBanner.start();
        headBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (advertBeanList.size() == 0) return;
                HomeBean.AdvertBean advertBean = advertBeanList.get(position);
                mPresenter.statisticClickNum(advertBean.getId());
                if (!TextUtils.isEmpty(advertBean.getLink_url())) {
                    //调外部链接
                    Intent intent = new Intent(HomeActivity.this, H5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.KEY, advertBean.getLink_url());
                    startActivity(intent);
                } else if (advertBean.getProduct_id() != 0) {
                    Intent intent = new Intent(HomeActivity.this, GoodsdetialsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.GOODS_ID, advertBean.getProduct_id());
                    startActivity(intent);
                }
            }
        });
        mPresenter.startup(1);
        initData();

        refreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.homedata(false);
            }
        });
    }



    private void initData() {

        Object query = CacheUtil.query(CacheUtil.HOME_ACTIVITY, HomeBean.class);
        if (query != null) {
            HomeBean bean = (HomeBean) query;
            goodsNumView.setBadgeCount(bean.getCartNum());
            goodsData(bean);
            getHeadBannerData(bean);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.homedata(true);
        mPresenter.versionInfo();

    }

    @OnClick({ R.id.print_bill_bt, R.id.leftpage_img, R.id.rightpage_img, R.id.cart_img})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.print_bill_bt:
                //补打小票
                openActivity(PrintSelectActivity.class);
                break;
            case R.id.leftpage_img:
                if (goodList.size() > 0)
                    banner.scrollLeft();
                break;
            case R.id.rightpage_img:
                if (goodList.size() > 0)
                    banner.scrollRight();
                break;
            case R.id.cart_img:
                //购物车
                openActivity(CartActivity.class);
                break;
        }
    }


    /**
     * goods banner
     *
     * @param data
     */
    private void goodsData(HomeBean data) {
        goodList = mPresenter.handerGoodsData(data.getList());
        if (goodList.size() == 0) {
            return;
        }
        banner.update(goodList);

    }


    private void getHeadBannerData(HomeBean data) {
        advertBeanList = data.getAdvert();
        List<String> picList = new ArrayList<>();
        for (HomeBean.AdvertBean advertBean : advertBeanList) {
            picList.add(advertBean.getImage_url());
        }
        headBanner.update(picList);
    }

    @Override
    public void onSuccess(Object o) {
        if (refreshlayout.isRefreshing())
            refreshlayout.setRefreshing(false);
        if (o == null) return;
        HomeBean data = (HomeBean) o;
        goodsNumView.setBadgeCount(data.getCartNum());
        goodsData(data);
        getHeadBannerData(data);

        String json = new Gson().toJson(data).toString();
        CacheBean bean = new CacheBean();
        bean.setId(CacheUtil.HOME_ACTIVITY_ID);
        bean.setJsonUrl(json);
        bean.setName(CacheUtil.HOME_ACTIVITY);
        CacheUtil.put(bean);
    }

    @Override
    public void onFail(Throwable throwable) {
        if (refreshlayout.isRefreshing())
            refreshlayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
    }

    @Override
    public void getVersionInfo(VersionInfoBean versionInfoBean) {
        if (versionInfoBean != null && versionInfoBean.getVersionName() != null && !versionInfoBean.getVersionName().equals(VersionManagerUtil.getVersion(this))) {
            DownLoadApk.download(this, URL.APK_LOADDOWN, "茂乐惠机", "mlhj");

        }
    }
}
