package com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Poputils;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.widget.GiveUpPayDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PayFragment extends MVPBaseFragment<PayContract.View, PayPresenter> implements PayContract.View, View.OnClickListener {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.goods_num_tv)
    TextView goodsNumTv;
    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.give_up_pay_tv)
    TextView giveUpPayTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    Unbinder unbinder;
    @BindView(R.id.bottom_left_img)
    ImageView bottomLeftImg;
    @BindView(R.id.bottom_right_img)
    ImageView bottomRightImg;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.pay_layout)
    LinearLayout payLayout;
    @BindView(R.id.weichatpay_tv)
    TextView weichatpayTv;
    @BindView(R.id.alipay_tv)
    TextView alipayTv;
    @BindView(R.id.wechat_code_ll)
    LinearLayout wechatCodeLl;
    @BindView(R.id.ali_code_ll)
    LinearLayout aliCodeLl;
    @BindView(R.id.fail_layyout)
    LinearLayout failLayyout;
    @BindView(R.id.paying_layout)
    LinearLayout payingLayout;
    private int payType;
    public static final String TIME_HINT = "剩%s秒自动关闭";

    private CallBackPayFragment callBackPayFragment;
    private OrderDetialBean bean;
    private CountDownTimer countDownTimer;
    private PopupWindow popupWindow;
    private View payView;
    private TextView time2Tv, codeNameTv, popPriceTv;
    private ImageView codeImg;
    private String authCode;
    private boolean isConfirmPay,isInterrupt;
    private boolean IS_WXPAY_CODE;


    public interface CallBackPayFragment {
        void onCallBack(int payType, OrderDetialBean orderDetialBean);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pay, null);
        unbinder = ButterKnife.bind(this, view);
        initPopView();
        initdata();
        return view;
    }

    private void initPopView() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        payView = layoutInflater.inflate(R.layout.layout_paycode, null);
        time2Tv = payView.findViewById(R.id.time2_tv);
        popPriceTv = payView.findViewById(R.id.price_tv);
        codeNameTv = payView.findViewById(R.id.code_name_tv);
        codeImg = payView.findViewById(R.id.code_img);
        payView.findViewById(R.id.confirm_payed_tv).setOnClickListener(this);
        payView.findViewById(R.id.select_other_pay_tv).setOnClickListener(this);
        payView.findViewById(R.id.dismiss_img).setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackPayFragment = (CallBackPayFragment) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        authCode = "";
    }

    private void initdata() {
        countDownTimer = new CountDownTimer(Constant.countDownTime, 1000) {
            @Override
            public void onTick(long l) {
                timeTv.setText(String.format(TIME_HINT, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                isInterrupt = true;
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        }.start();
        bean = (OrderDetialBean) getArguments().getSerializable(Constant.KEY);

        if (bean != null) {
            if (bean.isSelectWechat()) {
                mPresenter.changeOrderNo(bean.getOrder_id());
            }
            OrderDetialBean.ProductListBean productListBean1 = bean.getProduct_list().get(0);
            payType = bean.getPayType();
            ImgGlideUtil.displayImage(productListBean1.getProduct_image(), img, true);

            if (bean.getProduct_list().size() > 1) {
                nameTv.setText(productListBean1.getProduct_name() + "等");
            } else {
                nameTv.setText(productListBean1.getProduct_name() + "");
            }

            int num = 0;
            double price = 0.00;
            for (OrderDetialBean.ProductListBean productListBean : bean.getProduct_list()) {
                num = num + productListBean.getNum();
                price = price + Double.valueOf(productListBean.getPrice()) * productListBean.getNum();
            }
            Double allPrice = Double.valueOf(String.format("%.2f", price)) + Double.valueOf(bean.getFreight_amount());
            goodsNumTv.setText(String.format("共%d件商品", num));
            orderNoTv.setText(String.format("订单号%s", bean.getOrder_no()));
            priceTv.setText(String.format(Constant.PRICE_FORMAT, allPrice + ""));
            popPriceTv.setText(allPrice + "元");
            switchPayType();
        }

        codeEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    //处理事件
                    if (TextUtils.isEmpty(authCode)) {
                        if (TextUtils.isEmpty(codeEt.getText())) {
                            Toast.getInstance().toast(getContext(), "扫描失败，请重新扫描", 2);
                            return true;
                        } else {
                            payingLayout.setVisibility(View.VISIBLE);
                            authCode = codeEt.getText() + "";
                            payLayout.setVisibility(View.GONE);
                            failLayyout.setVisibility(View.GONE);
                            if (authCode.startsWith("1")) {//支付宝支付
                                mPresenter.scanPay(1, bean.getOrder_id(), authCode);
                            } else {
                                mPresenter.scanPay(2, bean.getOrder_id(), authCode);
                            }
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void getOrderInfo(OrderIdBean orderIdBean) {
        bean.setOrder_no(orderIdBean.getOrder_no());
        orderNoTv.setText(orderIdBean.getOrder_no() + "");
    }


    private void switchPayType() {
        if (payType == ConfirmOrderActivity.PAY_FAIL) {
            payingLayout.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
            failLayyout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 支付成功
     *
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        mPresenter.orderDetials(bean.getOrder_id());
    }

    /**
     * 支付失败
     *
     * @param throwable
     */
    @Override
    public void onFail(Throwable throwable) {
        payingLayout.setVisibility(View.GONE);
        payLayout.setVisibility(View.GONE);
        failLayyout.setVisibility(View.VISIBLE);
    }

    @Override
    public void orderDetial(OrderDetialBean orderDetialBean) {
        Toast.getInstance().toast(getContext(),"status:"+orderDetialBean.getPaid_status(),2);
        if (orderDetialBean.getPaid_status() == 1) {
            callBackPayFragment.onCallBack(ConfirmOrderActivity.PAY_SUCC, orderDetialBean);
        } else {
           if (!isConfirmPay&&!isInterrupt) {
                mPresenter.orderDetials(bean.getOrder_id());
            } else if(isConfirmPay){
                countDownTimer.start();
                payingLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                failLayyout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void orderDetialFial(Throwable throwable) {
        if(!isInterrupt)
        mPresenter.orderDetials(bean.getOrder_id());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        countDownTimer.cancel();
    }

    @OnClick({R.id.give_up_pay_tv, R.id.weichatpay_tv, R.id.alipay_tv, R.id.wechat_code_ll, R.id.ali_code_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.give_up_pay_tv:
                showGiveUpdialog();

                break;
            case R.id.weichatpay_tv:
                isInterrupt = true;
                isConfirmPay = false;
                mPresenter.changeOrderNo(bean.getOrder_id());
                payLayout.setVisibility(View.VISIBLE);
                failLayyout.setVisibility(View.GONE);
                payingLayout.setVisibility(View.GONE);
                payType = ConfirmOrderActivity.WECHAT_PAY;
                switchPayType();
                countDownTimer.start();
                break;
            case R.id.alipay_tv:
                isInterrupt = true;
                isConfirmPay = false;
                payLayout.setVisibility(View.VISIBLE);
                failLayyout.setVisibility(View.GONE);
                payingLayout.setVisibility(View.GONE);
                payType = ConfirmOrderActivity.ALI_PAY;
                switchPayType();
                countDownTimer.start();
                break;
            case R.id.wechat_code_ll:
                isConfirmPay = false;
                IS_WXPAY_CODE = true;
                codeNameTv.setText(Constant.WEIXIN_CODE);
                countDownTimer.start();
                mPresenter.getPayCode(bean.getOrder_id(), 1);
                break;
            case R.id.ali_code_ll:
                isConfirmPay = false;
                codeNameTv.setText(Constant.WALI_CODE);
                countDownTimer.start();
                mPresenter.getPayCode(bean.getOrder_id(), 2);
                break;
            case R.id.confirm_payed_tv:
                //确认支付
                isConfirmPay = true;
                countDownTimer.start();
                mPresenter.orderDetials(bean.getOrder_id());
                break;
            case R.id.select_other_pay_tv:
                isConfirmPay = false;
                isInterrupt = true;
                popupWindow.dismiss();
                break;
            case R.id.dismiss_img:
                showGiveUpdialog();
                break;
        }
    }

    private void showGiveUpdialog() {
        GiveUpPayDialog dialog = new GiveUpPayDialog(getContext());
        dialog.show();
        dialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                if (popupWindow != null && popupWindow.isShowing()) popupWindow.dismiss();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        isInterrupt = true;
    }

    @Override
    public void getPayCode(String codeUrl) {
        popupWindow = Poputils.getPop(payView, R.layout.layout_submitorder, getActivity());
        try {
            codeImg.setImageBitmap(CodeUtils.createQRCode(codeUrl, 300));
            mPresenter.orderDetials(bean.getOrder_id());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}
