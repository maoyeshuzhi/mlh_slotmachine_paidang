package com.maoye.mlh_slotmachine.view.printsuccactivity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.GlideImageLoader;
import com.maoye.mlh_slotmachine.widget.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PrintsuccActivity extends MVPBaseActivity<PrintsuccContract.View, PrintsuccPresenter> implements PrintsuccContract.View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.back)
    TextView back;
    private List<AdvertBean> list = new ArrayList<>();
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printsucc);
        ButterKnife.bind(this);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        mPresenter.getBannerData(5);
        initData();
    }

    private void initData() {
        timer = new CountDownTimer(361 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                back.setText(String.format("剩余操作时间%ds", l / 1000 - 1));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onSuccess(Object o) {
        list = (List<AdvertBean>) o;
        List<String> stringList = new ArrayList<>();
        for (AdvertBean advertBean : list) {
            stringList.add(advertBean.getImage_url());
        }
        banner.update(stringList);
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
