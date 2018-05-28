package com.maoye.mlh_slotmachine.view.goodsactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.util.httputil.cache.CacheUtil;
import com.maoye.mlh_slotmachine.widget.banner.BannerConfig;
import com.maoye.mlh_slotmachine.widget.banner.ViewBanner;
import com.maoye.mlh_slotmachine.widget.banner.transformer.BackgroundToForegroundTransformer;
import com.maoye.mlh_slotmachine.widget.banner.transformer.CubeOutTransformer;
import com.maoye.mlh_slotmachine.widget.banner.transformer.ScaleInOutTransformer;
import com.maoye.mlh_slotmachine.widget.banner.transformer.ZoomOutSlideTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


public class GoodsActivity extends MVPBaseActivity<GoodsContract.View, GoodsPresenter> implements GoodsContract.View {

    @BindView(R.id.leftpage_img)
    ImageView leftpageImg;
    @BindView(R.id.rightpage_img)
    ImageView rightpageImg;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.banner)
    ViewBanner banner;
    @BindView(R.id.back)
    ImageButton back;
    private List<List<GoodsBean>> goodList = new ArrayList<>();
    private int brandId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        banner.setViewLoader(new GoodsVH());
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
      //  banner.setPageTransformer(true, new ZoomOutSlideTransformer());//渐变
       // banner.setPageTransformer(true, new ScaleInOutTransformer());
        banner.setPageTransformer(true, new BackgroundToForegroundTransformer());
        //banner.setPageTransformer(true, new CubeOutTransformer());
        banner.start();

        Intent intent = getIntent();
        brandId = intent.getIntExtra(Constant.BRAND_ID, 0);
        Object query = CacheUtil.query(CacheUtil.GOODS_ACTIVITY+brandId, BrandGoodsBean.class);
        if (query != null) {
            BrandGoodsBean bean = (BrandGoodsBean) query;
            handData(bean);
        }

        mPresenter.goodsListData(brandId);
    }

    /**
     * 默认搜索的商品
     *
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        BrandGoodsBean brandGoodsBean = (BrandGoodsBean) o;
        handData(brandGoodsBean);
        String json = new Gson().toJson(brandGoodsBean).toString();
        CacheBean bean = new CacheBean();
        bean.setJsonUrl(json);
        bean.setName(CacheUtil.GOODS_ACTIVITY+brandId);
        CacheUtil.put(bean);

    }

    private void handData(BrandGoodsBean bean) {
        ImgGlideUtil.displayImage(bean.getBackground_image(), headImg, true);
        goodList = mPresenter.handerGoodsData(bean.getList());
        if(goodList.size()>1){
            rightpageImg.setVisibility(View.VISIBLE);
            leftpageImg.setVisibility(View.VISIBLE);
        }else {
            rightpageImg.setVisibility(View.GONE);
            leftpageImg.setVisibility(View.GONE);
        }
        if (goodList.size() == 0) {
            return;
        }

        banner.update(goodList);
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.leftpage_img, R.id.back,R.id.rightpage_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftpage_img:
                if (goodList.size() > 0)
                    banner.scrollLeft();
                break;
            case R.id.rightpage_img:
                if (goodList.size() > 0)
                    banner.scrollRight();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
