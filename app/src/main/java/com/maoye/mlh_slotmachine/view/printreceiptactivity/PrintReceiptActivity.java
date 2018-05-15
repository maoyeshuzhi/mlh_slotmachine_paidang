package com.maoye.mlh_slotmachine.view.printreceiptactivity;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.device.printers.PrinterUtils;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.printsdk.usbsdk.UsbDriver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 补打小票
 */
public class PrintReceiptActivity extends MVPBaseActivity<PrintreceiptContract.View, PrintreceiptPresenter> implements PrintreceiptContract.View {

    @BindView(R.id.flow2_tv)
    TextView flow2Tv;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.pay_bt)
    Button payBt;
    @BindView(R.id.goshop_bt)
    Button goshopBt;
    @BindView(R.id.back_bt)
    ImageView backBt;

    @BindView(R.id.print_tep_img)
    ImageView printTepImg;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.succ_tv)
    TextView succTv;
    @BindView(R.id.time_back_tv)
    TextView timeBackTv;
    @BindView(R.id.print_title_tv)
    TextView printTitleTv;
    private UsbManager mUsbManager;
    UsbDriver mUsbDriver;
    private static final String ACTION_USB_PERMISSION = "com.usb.sample.USB_PERMISSION";
    public static final String TIME1 = "返回首页%ss";
    public static final String TIME2 = "剩余操作时长%s秒";
    private CountDownTimer countDownTimer1, countDownTimer2;
    private String authCode = "";
    private boolean isPrintSucc;
    private String orderId = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_receipt);
        ButterKnife.bind(this);
        getUsbDriverService();
        initData();

    }

    private void initData() {
        countDownTimer1 = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timeBackTv.setText(String.format(TIME1, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                openActivity(HomeActivity.class);

            }
        };
        countDownTimer2 = new CountDownTimer(360 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timeTv.setText(String.format(TIME2, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                openActivity(HomeActivity.class);
            }
        }.start();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(codeEt.getWindowToken(),0);

     codeEt.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
         }

         @Override
         public void afterTextChanged(Editable editable) {
             LogUtils.e(editable+"");

         }
     });

        codeEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    //处理事件
                     authCode = codeEt.getText().toString();
                    String[] split = authCode.split("=");
                    codeEt.setText("");
                    if (split[split.length-1]!=null&&!orderId.equals(split[split.length-1])) {
                        try {
                            orderId = split[split.length-1];
                            LogUtils.e("orderid"+orderId);
                            mPresenter.OrderDetial(Integer.valueOf(orderId));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void getUsbDriverService() {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mUsbDriver = new UsbDriver(mUsbManager, getContext());
        PendingIntent permissionIntent1 = PendingIntent.getBroadcast(getContext(), 0,
                new Intent(ACTION_USB_PERMISSION), 0);
        mUsbDriver.setPermissionIntent(permissionIntent1);

    }

    @Override
    public void onSuccess(Object o) {
     /*   代发货：
        待取货：30
        待收货：20
        已完成：81、82*/
        OrderDetialBean bean = (OrderDetialBean) o;
        int status = bean.getOrder_status();
       if(status ==10||status ==20||status ==30||status ==81||status ==82) {
           PrinterUtils printerUtils = PrinterUtils.getInstanse();
           if (!printerUtils.PrintConnStatus(mUsbDriver, mUsbManager)) {
               if(!isPrintSucc) orderId = "";
               return;
           }
           isPrintSucc = true;
           printerUtils.getPrintTicketData(mUsbDriver, bean, this,0);
           flow2Tv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.b5), null, null);
           printTitleTv.setVisibility(View.GONE);
           printTepImg.setVisibility(View.GONE);
           succTv.setVisibility(View.VISIBLE);
           timeBackTv.setVisibility(View.VISIBLE);
           countDownTimer1.start();
       }else {
           Toast.getInstance().toast(this,"该订单状态无法打印小票",2);
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer1.cancel();
        countDownTimer2.cancel();
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.pay_bt, R.id.goshop_bt, R.id.back_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_bt:
                //快付买单
                Toast.getInstance().toast(this,"正在开发中,敬请期待",2);
                break;
            case R.id.goshop_bt:
            case R.id.back_bt:
                openActivity(HomeActivity.class);
                break;
        }
    }
}
