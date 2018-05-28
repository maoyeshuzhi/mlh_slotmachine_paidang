package com.maoye.mlh_slotmachine.view.print_select_activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.view.h5activity.H5Activity;
import com.maoye.mlh_slotmachine.view.homeactivity.GlideImageLoader;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.view.printreceiptactivity.PrintReceiptActivity;
import com.maoye.mlh_slotmachine.view.quickprintactivity.QuickprintActivity;
import com.maoye.mlh_slotmachine.widget.banner.Banner;
import com.maoye.mlh_slotmachine.widget.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PrintSelectActivity extends MVPBaseActivity<Print_selectContract.View, Print_selectPresenter> implements Print_selectContract.View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.quick_pay_print_bt)
    Button quickPayPrintBt;
    @BindView(R.id.mlhj_print_bt)
    Button mlhjPrintBt;
    @BindView(R.id.back_bt)
    ImageButton backBt;
    @BindView(R.id.time_tv)
    TextView timeTv;
    private List<AdvertBean> list = new ArrayList<>();
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_select);
        ButterKnife.bind(this);
        initdata();
    }

    private void initdata() {
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        mPresenter.getBannerData(4);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.size() == 0) return;
                AdvertBean advertBean = list.get(position);
                mPresenter.statisticClickNum(advertBean.getId());
                if (!TextUtils.isEmpty(advertBean.getLink_url())) {
                    //调外部链接
                    Intent intent = new Intent(PrintSelectActivity.this, H5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.KEY, advertBean.getLink_url());
                    startActivity(intent);
                } else if (advertBean.getProduct_id() != 0) {
                    Intent intent = new Intent(PrintSelectActivity.this, GoodsdetialsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.GOODS_ID, advertBean.getProduct_id());
                    startActivity(intent);
                }
            }
        });
        timer = new CountDownTimer(Constant.quickPaycountDownTime,1000) {
            @Override
            public void onTick(long l) {
                timeTv.setText(String.format(Constant.countDownTimeFormat2,l/1000-1)+"");
            }

            @Override
            public void onFinish() {
                   openActivity(HomeActivity.class);
                   finish();
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

    @OnClick({R.id.quick_pay_print_bt, R.id.mlhj_print_bt, R.id.back_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quick_pay_print_bt:
                openActivity(QuickprintActivity.class);
                break;
            case R.id.mlhj_print_bt:
                openActivity(PrintReceiptActivity.class);
                break;
            case R.id.back_bt:
                finish();
                break;
        }
    }
}
