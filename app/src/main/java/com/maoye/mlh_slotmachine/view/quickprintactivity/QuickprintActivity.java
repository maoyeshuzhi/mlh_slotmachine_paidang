package com.maoye.mlh_slotmachine.view.quickprintactivity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.QuickGoodsDetialAdapter;
import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseInfo;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DateUtils;
import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.device.printers.PrinterUtils;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.view.h5activity.H5Activity;
import com.maoye.mlh_slotmachine.view.homeactivity.GlideImageLoader;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.widget.NotScrollRecyclerView;
import com.maoye.mlh_slotmachine.widget.banner.Banner;
import com.maoye.mlh_slotmachine.widget.banner.listener.OnBannerListener;
import com.printsdk.usbsdk.UsbDriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuickprintActivity extends MVPBaseActivity<QuickprintContract.View, QuickprintPresenter> implements QuickprintContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.confirm_bt)
    Button confirmBt;

    @BindView(R.id.query_bt)
    Button queryBt;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.time_tv)
    Button timeTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.recycler)
    NotScrollRecyclerView recycler;
    @BindView(R.id.countdowntimer_tv)
    TextView countdowntimerTv;
    @BindView(R.id.spaceview)
    View spaceview;
    @BindView(R.id.scancode_rb)
    RadioButton scancodeRb;
    @BindView(R.id.inputcode_rb)
    RadioButton inputcodeRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.scancode_et)
    EditText scancodeEt;
    @BindView(R.id.scan_layout)
    LinearLayout scanLayout;
    private List<AdvertBean> list = new ArrayList<>();
    private String sapId;
    public static final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;
    private UsbManager mUsbManager;
    UsbDriver mUsbDriver;
    private static final String ACTION_USB_PERMISSION = "com.usb.sample.USB_PERMISSION";
    private QuickGoodsDetialAdapter adapter;
    private List<QuickOrderDetialsBean.SaledListBean> orderList = new ArrayList<>();
    private QuickOrderDetialsBean bean;
    private CountDownTimer countDownTimer;
    private String code="",saleNo="";
    private int selectType;
    public static final int SCAN =0;
    public  static final int INPUT =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay_print);
        ButterKnife.bind(this);
        initdata();
    }

    private void initdata() {
        String sapId = BaseInfo.getSapId();
        Log.e("Tag", "sapId: " + sapId);
        mPresenter.getSapId(DeviceInfoUtil.getDeviceId());
        adapter = new QuickGoodsDetialAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        Date time = Calendar.getInstance().getTime();
        mYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(time));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM").format(time));
        mDay = Integer.parseInt(new SimpleDateFormat("dd").format(time));
        timeTv.setText(DateUtils.format(time, DateUtils.Pattern.YYYYMMDD));
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        getUsbDriverService();
        mPresenter.getBannerData(6);
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && (editable.length()==8 || editable.length()==10)) {
                    queryBt.setEnabled(true);
                    queryBt.setClickable(true);
                    queryBt.setBackgroundColor(getResources().getColor(R.color.color_dd2450));
                } else {
                    queryBt.setEnabled(false);
                    queryBt.setClickable(false);
                    queryBt.setBackgroundColor(getResources().getColor(R.color.color_b4b4b4));
                }
            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.size() == 0) return;
                AdvertBean advertBean = list.get(position);
                mPresenter.statisticClickNum(advertBean.getId());
                if (!TextUtils.isEmpty(advertBean.getLink_url())) {
                    //调外部链接
                    Intent intent = new Intent(QuickprintActivity.this, H5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.KEY, advertBean.getLink_url());
                    startActivity(intent);
                } else if (advertBean.getProduct_id() != 0) {
                    Intent intent = new Intent(QuickprintActivity.this, GoodsdetialsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.GOODS_ID, advertBean.getProduct_id());
                    startActivity(intent);
                }
            }
        });
        countDownTimer = new CountDownTimer(Constant.quickPaycountDownTime, 1000) {
            @Override
            public void onTick(long l) {
                countdowntimerTv.setText(String.format(Constant.countDownTimeFormat2, l / 1000 - 1));
            }

            @Override
            public void onFinish() {
                openActivity(HomeActivity.class);
                finish();
            }
        }.start();


        scancodeEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    //处理事件
                  String  authCode = scancodeEt.getText().toString();
                    if(authCode.startsWith("saleNo=")) {
                        code = authCode;
                        String[] split = authCode.split("=");
                        scancodeEt.setText("");
                        if (split[split.length - 1] != null && !saleNo.equals(split[split.length - 1])) {
                            try {
                                saleNo = split[split.length - 1];
                                mPresenter.orderData(saleNo);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.getInstance().toast(getApplicationContext(),"请勿重复打印！",2);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
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

    @OnClick({R.id.confirm_bt, R.id.back, R.id.time_tv, R.id.query_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_bt:
                if (sapId == null) {
                    Toast.getInstance().toast(this, "正在获取门店职位中,稍后请再次尝试", 2);
                } else {
                    String saleNo = sapId + timeTv.getText() + codeEt.getText();
                    mPresenter.orderData(saleNo);
                }
                break;
            case R.id.confirm_bt://校验处
                PrinterUtils printerUtils = PrinterUtils.getInstanse();
                if (!printerUtils.PrintConnStatus(mUsbDriver, mUsbManager)) {
                    return;
                }
                switchClick(false);

                boolean printTicketData = printerUtils.getPrintTicketData(mUsbDriver, bean, this);
                if (printTicketData) {
                    Toast.getInstance().toast(this, Constant.PRINT_SUCC, 2);
                    mPresenter.markPrint(bean.getSaleNo());
                    codeEt.setText("");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.time_tv:
                showDialog(DATE_DIALOG);
                break;
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
               // DatePickerDialog.THEME_HOLO_DARK
                //DatePickerDialog.THEME_HOLO_LIGHT
               // R.style.MyDatePickerDialogTheme
                DatePickerDialog dialog = new DatePickerDialog(this,  R.style.MyDatePickerDialogTheme, mdateListener, mYear, mMonth, mDay);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setMinDate(DateUtils.getPeriodDate(30));
                datePicker.setMaxDate(System.currentTimeMillis() + 100000);
                return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            int i = monthOfYear + 1;
            String month = mMonth < 9 ? ("0" + i) : i + "";
            String day = mDay < 10 ? ("0" + mDay) : mDay + "";
            timeTv.setText(mYear + "" + month + day);
        }
    };


    @Override
    public void getSapId(String sapId) {
        this.sapId = sapId;
    }

    @Override
    public void getOrderDetials(BaseResult<QuickOrderDetialsBean> beanBaseResult) {
        bean = beanBaseResult.getData();
        if(bean.getPrintCount()>=3){
            Toast.getInstance().toast(this,"纸质小票已打印三次，无法再打印！",2);
            if(selectType == SCAN) scancodeEt.setText("");
             return;
        }

        if(selectType == SCAN){
            if (bean == null) {
                scancodeEt.setText("");
                Toast.getInstance().toast(getApplicationContext(), "不存在此订单", 2);
                return;
            }
            PrinterUtils printerUtils = PrinterUtils.getInstanse();
            if (!printerUtils.PrintConnStatus(mUsbDriver, mUsbManager)) {
                saleNo = "";
                code = "";
                scancodeEt.setText("");
                return;
            }
            switchClick(false);
            boolean printTicketData = printerUtils.getPrintTicketData(mUsbDriver, bean, this);
            if (printTicketData) {
                Toast.getInstance().toast(this, Constant.PRINT_SUCC, 2);
                mPresenter.markPrint(bean.getSaleNo());
                scancodeEt.setText("");
            }else {
                saleNo = "";
                code = "";
                scancodeEt.setText("");
            }

        }else if(selectType ==INPUT){
            if (beanBaseResult != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(codeEt.getWindowToken(), 0);
                if (bean == null) {
                    Toast.getInstance().toast(getApplicationContext(), "不存在此订单", 2);
                    return;
                }
                scrollView.setVisibility(View.VISIBLE);
                spaceview.setVisibility(View.GONE);
                orderList = bean.getSaledList();
                adapter.addDatas(orderList);
                double price = 0.00;
                switchClick(true);
                orderNoTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.select), null, null, null);
                for (QuickOrderDetialsBean.SaledListBean saledListBean : orderList) {
                    price = price + saledListBean.getSaleAmount() - saledListBean.getCouponAmount();
                }
                priceTv.setText(String.format("合计：￥%s", String.format("%.2f", price)));
            } else {
                scrollView.setVisibility(View.GONE);
                spaceview.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getOrderDetialsFial(Throwable t) {
        spaceview.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        if(selectType == SCAN){
            saleNo = "";
            code = "";
            scancodeEt.setText("");
        }
    }


    private void switchClick(boolean isClick) {
        if (isClick) {
            confirmBt.setBackgroundColor(getResources().getColor(R.color.color_dd2450));
            confirmBt.setEnabled(true);
            confirmBt.setClickable(true);
        } else {
            confirmBt.setBackgroundColor(getResources().getColor(R.color.color_b4b4b4));
            confirmBt.setEnabled(false);
            confirmBt.setClickable(false);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.scancode_rb:
                switchType(0);
                break;
            case R.id.inputcode_rb:
                switchType(1);
                break;
        }
    }

    private void switchType(int type) {
        selectType = type;
        switchClick(false);
        if (type == 0) {
            scancodeEt.setFocusable(true);
            scancodeEt.setText("");
            orderList.clear();
            scrollView.setVisibility(View.GONE);
            spaceview.setVisibility(View.VISIBLE);
            confirmBt.setVisibility(View.GONE);
            inputLayout.setVisibility(View.GONE);
            scanLayout.setVisibility(View.VISIBLE);
            scancodeRb.setTextColor(getResources().getColor(R.color.white));
            inputcodeRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
        } else if (type == 1) {
            confirmBt.setVisibility(View.VISIBLE);
            inputLayout.setVisibility(View.VISIBLE);
            scanLayout.setVisibility(View.GONE);
            scancodeRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            inputcodeRb.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
