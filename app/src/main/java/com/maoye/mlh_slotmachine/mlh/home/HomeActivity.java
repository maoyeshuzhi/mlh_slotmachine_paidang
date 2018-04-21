package com.maoye.mlh_slotmachine.mlh.home;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mlh.cart.CartActivity;
import com.maoye.mlh_slotmachine.mlh.goodsdetials.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.widget.BadgeView;
import com.maoye.mlh_slotmachine.widget.banner.ConvenientBanner;
import com.maoye.mlh_slotmachine.widget.banner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends MVPBaseActivity<HomeContract.View, HomePresenter> implements HomeContract.View {
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.quick_pay_bt)
    TextView quickPayBt;
    @BindView(R.id.print_bill_bt)
    TextView printBillBt;
    @BindView(R.id.leftpage_img)
    ImageView leftpageImg;
    @BindView(R.id.rightpage_img)
    ImageView rightpageImg;
    @BindView(R.id.head_banner)
    ConvenientBanner headBanner;
    @BindView(R.id.cart_img)
    ImageView cartImg;
    @BindView(R.id.print_ll)
    LinearLayout printLl;
    private BadgeView goodsNumView;

    private List<List<HomeBean.ListBeanX>> goodList = new ArrayList<>();
    private List<HomeBean.AdvertBean> advertBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        goodsNumView = new BadgeView(HomeActivity.this);
        goodsNumView.setTargetView(cartImg);
        goodsNumView.setBadgeMargin(0, 2, 10, 0);
        //goodsNumView.setPadding(4,2,2,4);
        headBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                HomeBean.AdvertBean advertBean = advertBeanList.get(position);
                mPresenter.statisticClickNum(advertBean.getId());
                if(TextUtils.isEmpty(advertBean.getLink_url())){
                    //调外部链接
                    Uri uri = Uri.parse(advertBean.getLink_url());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else if(advertBean.getProduct_id()!=0){
                    Intent intent = new Intent(HomeActivity.this, GoodsdetialsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.GOODS_ID,advertBean.getProduct_id());
                    startActivity(intent);
                }
            }


        });

        mPresenter.homedata("mlhj01");
    }



    @OnClick({R.id.quick_pay_bt, R.id.print_bill_bt, R.id.leftpage_img, R.id.rightpage_img, R.id.cart_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quick_pay_bt:
                //快付买单
                break;
            case R.id.print_bill_bt:
                //补打小票
                break;
            case R.id.leftpage_img:
                banner.setcurrentitem(banner.getViewPager().getRealItem() - 1);
                break;
            case R.id.rightpage_img:
                banner.setcurrentitem(banner.getViewPager().getRealItem() + 1);
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
        banner.setCanLoop(true);
        banner.setPages(new CBViewHolderCreator<HomeGoodsVH>() {
            @Override
            public HomeGoodsVH createHolder() {
                return new HomeGoodsVH();
            }
        }, goodList);
    }

    private void getHeadBannerData(HomeBean data) {
        advertBeanList = data.getAdvert();
        headBanner.setPages(new CBViewHolderCreator<HomeHeadVH>() {
            @Override
            public HomeHeadVH createHolder() {
                return new HomeHeadVH();
            }
        }, advertBeanList);
    }

    @Override
    public void onSuccess(Object o) {
        if (o == null) return;
        HomeBean data =  (HomeBean) o;
        goodsNumView.setBadgeCount(data.getCartNum());
        goodsData(data);
        getHeadBannerData(data);
    }

    @Override
    public void onFail(Throwable throwable) {

    }
}
