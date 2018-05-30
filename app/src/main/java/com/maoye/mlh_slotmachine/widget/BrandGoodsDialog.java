package com.maoye.mlh_slotmachine.widget;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.BrandGoodsGuidesAdapter;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsListBean;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rs on 2018/5/28.
 */

public class BrandGoodsDialog extends AlertDialog  {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshlayout;
    @BindView(R.id.dismiss_imgbt)
    ImageButton dismissImgbt;
    private BrandGoodsGuidesAdapter adapter;
    private List<BrandGoodsListBean.ListBean> list = new ArrayList<>();
    private ImageView imageView;
    private int page = 2, totalPage;
    private String brandId;
   private Activity context;
    public BrandGoodsDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    public void setData(BrandGoodsListBean bean, String brandId) {
        ImgGlideUtil.displayImage(bean.getBrand_image(), imageView, true);
        totalPage = bean.getTotal()/bean.getLimit()+1;
        this.list = bean.getList();
        this.brandId = brandId;
        adapter.addDatas(list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImmersionBar.with(context).init();
        setContentView(R.layout.dialog_brandgoods);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setSwipeHeader(refreshlayout, getContext());
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new BrandGoodsGuidesAdapter();
        adapter.setContext(context);
        recycler.setAdapter(adapter);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.layout_head_img, null);
        imageView = headView.findViewById(R.id.head_img);
        adapter.setHeaderView(headView);
        initData();

        //填充整个屏幕
        Window dialogWindow = this.getWindow();
        WindowManager windowManager = dialogWindow.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        lp.height = (int)(display.getHeight()*1.2); //设置高度
        dialogWindow.setAttributes(lp);
    }

    private void initData() {
        refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                requestData(Constant.REFRESH);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (page <= totalPage) {
                    requestData(Constant.LOADMORE);
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });

    }

    public void requestData(final int type) {
        BaseModel model = new BaseModel();
        Observable observable = model.mServletApi.brandDetial(brandId, page, 10);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS)
                .subscribe(new BaseObserver<BaseResult<BrandGoodsListBean>>(getContext(), false) {
                    @Override
                    protected void onBaseNext(BaseResult<BrandGoodsListBean> data) {
                        totalPage = data.getData().getTotal() / data.getData().getLimit() + 1;
                        ++page;
                        if (type == Constant.REFRESH) {
                            refreshlayout.finishRefreshing();
                            adapter.addDatas(data.getData().getList());
                        } else if (type == Constant.LOADMORE) {
                            list.addAll(data.getData().getList());
                            adapter.addDatas(list);
                            refreshlayout.finishLoadmore();
                        }
                    }

                    @Override
                    protected void onBaseError(Throwable t) {
                        if (type == Constant.REFRESH) {
                            refreshlayout.finishRefreshing();
                        } else if (type == Constant.LOADMORE) {
                            refreshlayout.finishLoadmore();
                        }
                    }
                });   //订阅
    }


    /**
     * 设置下拉刷新头部
     *
     * @param refreshLayout
     * @param context
     */
    public static void setSwipeHeader(TwinklingRefreshLayout refreshLayout, Context context) {
        ProgressLayout headerView = new ProgressLayout(context);
        headerView.setSize(10);
        // headerView.setBackgroundResource(android.R.color.holo_blue_bright);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setEnableOverScroll(false);
        // headerView.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
        headerView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        LoadingView loadingView = new LoadingView(context);

        refreshLayout.setBottomView(loadingView);
    }

    @OnClick(R.id.dismiss_imgbt)
    public void onClick() {
        dismiss();
    }
}
