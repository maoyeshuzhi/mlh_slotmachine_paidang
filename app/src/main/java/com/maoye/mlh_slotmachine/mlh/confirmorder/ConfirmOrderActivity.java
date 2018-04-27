package com.maoye.mlh_slotmachine.mlh.confirmorder;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.event.GoodsEventBean;
import com.maoye.mlh_slotmachine.mlh.confirmorder.confirm.ConfirmFragment;
import com.maoye.mlh_slotmachine.mlh.confirmorder.payfragment.PayFragment;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;



import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends MVPBaseActivity<ConfirmorderContract.View, ConfirmorderPresenter> implements ConfirmorderContract.View, ConfirmFragment.CallBackConfirmFragment {
    @BindView(R.id.flow1_tv)
    TextView flow1Tv;
    @BindView(R.id.flow2_tv)
    TextView flow2Tv;
    @BindView(R.id.flow3_tv)
    TextView flow3Tv;
    @BindView(R.id.flow4_tv)
    TextView flow4Tv;
    @BindView(R.id.flow5_tv)
    TextView flow5Tv;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    public static final int CONFIRMF_RAGGMENT = 0;//提交订单成功
    public static final int WECHAT_PAY = 1;
    public static final int ALI_PAY = 2;
    @BindView(R.id.cart_arrowline_img)
    ImageView cartArrowlineImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        if (getIntent().getIntExtra(Constant.FROM, 0) == 0) {
            cartArrowlineImg.setVisibility(View.GONE);
            flow1Tv.setVisibility(View.GONE);
        }
        fragmentManager(R.id.fragment_container, new ConfirmFragment(), "MeFragment");
    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @Override
    public void onCallBack(int status, GoodsEventBean bean) {
        switch (status) {
            case CONFIRMF_RAGGMENT://提交订单
                flow3Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b3), null, null);
                break;
            case WECHAT_PAY://微信支付
            case ALI_PAY://支付宝支付
                PayFragment payFragment = new PayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY, bean);
                payFragment.setArguments(bundle);
                // EventBus.getDefault().post(bean);
                fragmentManager(R.id.fragment_container,payFragment, "payfragment");
                break;

        }
    }
}
