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
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.widget.GiveUpPayDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PayFragment extends MVPBaseFragment<PayContract.View, PayPresenter> implements PayContract.View, View.OnClickListener {

    Unbinder unbinder;
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
    @BindView(R.id.left_icon)
    ImageView leftIcon;
    @BindView(R.id.change_tv)
    TextView changeTv;
    @BindView(R.id.right_icon)
    ImageView rightIcon;
    @BindView(R.id.scan_img)
    ImageView scanImg;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.pay_layout)
    LinearLayout payLayout;
    @BindView(R.id.weichatpay_tv)
    TextView weichatpayTv;
    @BindView(R.id.alipay_tv)
    TextView alipayTv;
    @BindView(R.id.fail_layyout)
    LinearLayout failLayyout;
    @BindView(R.id.paying_layout)
    LinearLayout payingLayout;
    @BindView(R.id.give_up_pay_tv)
    TextView giveUpPayTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.payhint_tv)
    TextView payhintTv;
    private int payType;
    public static final String TIME_HINT = "剩%s秒自动关闭";
    private CallBackPayFragment callBackPayFragment;
    private OrderDetialBean bean;
    private CountDownTimer countDownTimer;
    private String authCode;
    private boolean isInterrupt;


    public interface CallBackPayFragment {
        void onCallBack(int payType, OrderDetialBean orderDetialBean);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pay, null);
        unbinder = ButterKnife.bind(this, view);
        initdata();
        return view;
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
                            codeEt.setFocusable(false);
                            codeEt.setVisibility(View.GONE);
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
    public void getOrderInfo(OrderIdBean orderIdBean, boolean isClickWXCode) {
        bean.setOrder_no(orderIdBean.getOrder_no());
        orderNoTv.setText(orderIdBean.getOrder_no() + "");
    }


    private void switchPayType() {
        if (payType == ConfirmOrderActivity.PAY_FAIL) {
            payingLayout.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
            failLayyout.setVisibility(View.VISIBLE);
        } else if (payType == ConfirmOrderActivity.ALI_PAY) {
            payingLayout.setVisibility(View.GONE);
            failLayyout.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            leftIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.alipay_icon));
            rightIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_icon));
            changeTv.setText("切换到微信付款");
            scanImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_new));
            payhintTv.setText(R.string.alipayhint_str);
        } else if (payType == ConfirmOrderActivity.WECHAT_PAY) {
            payingLayout.setVisibility(View.GONE);
            failLayyout.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            leftIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_icon));
            rightIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.alipay_icon));
            changeTv.setText("切换到支付宝付款");
            payhintTv.setText(R.string.wxpayhint_str);
            scanImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.weixin_new));
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
            callBackPayFragment.onCallBack(ConfirmOrderActivity.PAY_SUCC, orderDetialBean);
    }

    @Override
    public void orderDetialFial(Throwable throwable) {
        if (!isInterrupt) mPresenter.orderDetials(bean.getOrder_id());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isInterrupt = true;
        countDownTimer.cancel();
    }

    @OnClick({R.id.give_up_pay_tv, R.id.left_icon, R.id.weichatpay_tv, R.id.alipay_tv,R.id.right_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_icon:
                if(payType == ConfirmOrderActivity.ALI_PAY){
                    payType = ConfirmOrderActivity.WECHAT_PAY;
                }else {
                    payType = ConfirmOrderActivity.ALI_PAY;
                }
                switchPayType();
                break;
            case R.id.right_icon:
                if(payType == ConfirmOrderActivity.ALI_PAY){
                    payType = ConfirmOrderActivity.WECHAT_PAY;
                }else {
                    payType = ConfirmOrderActivity.ALI_PAY;
                }
                switchPayType();
                break;
            case R.id.give_up_pay_tv:
                showGiveUpdialog();
                break;
            case R.id.weichatpay_tv:
                codeEt.setFocusable(true);
                codeEt.setVisibility(View.VISIBLE);
                payType = ConfirmOrderActivity.WECHAT_PAY;
                switchPayType();
                countDownTimer.start();
                break;
            case R.id.alipay_tv:
                codeEt.setFocusable(true);
                codeEt.setVisibility(View.VISIBLE);
                payType = ConfirmOrderActivity.ALI_PAY;
                switchPayType();
                countDownTimer.start();
                break;
        }
    }

    private void showGiveUpdialog() {
        GiveUpPayDialog dialog = new GiveUpPayDialog(getContext());
        dialog.show();
        dialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}
