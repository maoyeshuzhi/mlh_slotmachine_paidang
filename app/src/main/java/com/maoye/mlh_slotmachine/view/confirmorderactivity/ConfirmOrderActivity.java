package com.maoye.mlh_slotmachine.view.confirmorderactivity;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.MD5;
import com.maoye.mlh_slotmachine.util.device.printers.PrinterUtils;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment.ConfirmFragment;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment.PayFragment;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.succfragment.SuccFragment;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;
import com.printsdk.usbsdk.UsbDriver;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends MVPBaseActivity<ConfirmorderContract.View, ConfirmorderPresenter> implements ConfirmorderContract.View, ConfirmFragment.CallBackConfirmFragment, PayFragment.CallBackPayFragment {
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
    public static final int WECHAT_PAY = 3;
    public static final int ALI_PAY = 4;
    public static final int ALI_CODE_PAY = 1;
    public static final int WECHAT_CODE_PAY = 2;
    public static final int PAY_SUCC = 5;
    public static final int PAY_FAIL = 6;
    @BindView(R.id.cart_arrowline_img)
    ImageView cartArrowlineImg;

    private static final String ACTION_USB_PERMISSION = "com.usb.sample.USB_PERMISSION";
    private UsbManager mUsbManager;
    UsbDriver mUsbDriver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        initData();
        getUsbDriverService();
    }


    private void initData() {
        if (getIntent().getIntExtra(Constant.FROM, 0) == 0) {
            cartArrowlineImg.setVisibility(View.GONE);
            flow1Tv.setVisibility(View.GONE);
            flow2Tv.setText("①拍下商品");
            flow3Tv.setText("②快递/门店自提配送");
            flow4Tv.setText("③选择支付方式");
            flow5Tv.setText("④完成打小票");
        }
        flow2Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b2), null, null);
        fragmentManager(R.id.fragment_container, new ConfirmFragment(), "MeFragment");
    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {
    }


    @Override
    public void onCallBack(int status, OrderDetialBean bean) {
        switch (status) {
            case CONFIRMF_RAGGMENT://提交订单
                flow3Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b3), null, null);

                break;
            case WECHAT_PAY://微信支付
            case ALI_PAY://支付宝支付
            case PAY_FAIL:
                flow4Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b4), null, null);
                PayFragment payFragment = new PayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY, bean);
                payFragment.setArguments(bundle);
                fragmentManager(R.id.fragment_container, payFragment, "payfragment");
                break;
            case PAY_SUCC:
                 SuccFragment succFragment = new SuccFragment();
                Bundle succBundle = new Bundle();
                succBundle.putSerializable(Constant.KEY, bean);
                succFragment.setArguments(succBundle);
                flow5Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b5), null, null);
                fragmentManager(R.id.fragment_container, succFragment, "succFragment");
                //打印小票
                PrinterUtils printerUtils = PrinterUtils.getInstanse();
                if (!printerUtils.PrintConnStatus(mUsbDriver, mUsbManager)) {
                    return;
                }
                boolean printTicketData = printerUtils.getPrintTicketData(mUsbDriver, bean, this, 0);
                if(printTicketData){
                    mPresenter.markOrder(bean.getOrder_id());
                }
                break;

        }
    }


    private void getUsbDriverService() {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mUsbDriver = new UsbDriver(mUsbManager, getContext());
        PendingIntent permissionIntent1 = PendingIntent.getBroadcast(getContext(), 0,
                new Intent(ACTION_USB_PERMISSION), 0);
        mUsbDriver.setPermissionIntent(permissionIntent1);
    }

}
