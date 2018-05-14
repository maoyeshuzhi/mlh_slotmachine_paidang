package com.maoye.mlh_slotmachine.view.adactivity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.MlhApplication;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AdBean;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.CacheBean_;
import com.maoye.mlh_slotmachine.util.NetworkUtil;
import com.maoye.mlh_slotmachine.view.homeactivity.GlideImageLoader;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.httputil.cache.CacheUtil;
import com.maoye.mlh_slotmachine.widget.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;


/**
 * 屏保广告
 */
public class AdActivity extends MVPBaseActivity<AdactivityContract.View, AdactivityPresenter> implements AdactivityContract.View {

    @BindView(R.id.banner)
    Banner banner;
    private List<AdBean.DataBean> list = new ArrayList<>();
    private Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        initData();

    }

    private void initData() {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        List<CacheBean> cacheBeans = cacheBeanBox.query().equal(CacheBean_.name, CacheUtil.ADS).build().find();
        if (cacheBeans != null && cacheBeans.size() > 0) {
            AdBean adBean = new Gson().fromJson(cacheBeans.get(cacheBeans.size() - 1).getJsonUrl(), AdBean.class);
            list = adBean.getData();
            List<String> resultList = new ArrayList<>();
            for (AdBean.DataBean dataBean : list) {
                resultList.add(dataBean.getLocal_img_url());
            }
            banner.update(resultList);
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag =true;
                while (flag){
                    if (NetworkUtil.checkNetworkAvailable(getApplicationContext())) {
                        AdActivity.this.finish();
                        flag =false;
                    }
                }

            }
        });
        thread.start();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.releaseBanner();
        thread.interrupt();

    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }
}
