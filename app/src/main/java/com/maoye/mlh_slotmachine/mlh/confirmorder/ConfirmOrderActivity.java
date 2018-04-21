package com.maoye.mlh_slotmachine.mlh.confirmorder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends MVPBaseActivity<ConfirmorderContract.View, ConfirmorderPresenter> implements ConfirmorderContract.View {

    @BindView(R.id.flow2_img)
    ImageView flow2Img;
    @BindView(R.id.flow3_img)
    ImageView flow3Img;
    @BindView(R.id.flow4_img)
    ImageView flow4Img;
    @BindView(R.id.flow5_img)
    ImageView flow5Img;
    @BindView(R.id.pickup_rb)
    RadioButton pickupRb;
    @BindView(R.id.express_rb)
    RadioButton expressRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.stub_import)
    ViewStub stubImport;
    @BindView(R.id.back_imgbt)
    ImageButton backImgbt;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.submit_bt)
    Button submitBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
    }



    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.back_imgbt, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imgbt:
                break;
            case R.id.submit_bt:
                break;
        }
    }
}
