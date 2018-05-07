package com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
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
import com.maoye.mlh_slotmachine.view.confirmorderactivity.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Poputils;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;

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
    @BindView(R.id.pay_max_img)
    ImageView payMaxImg;
    @BindView(R.id.pay_max_tv)
    TextView payMaxTv;
    @BindView(R.id.min_img)
    ImageView minImg;
    @BindView(R.id.min_tv)
    TextView minTv;
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
    private TextView time2Tv, codeNameTv,popPriceTv;
    private ImageView codeImg;
    private boolean isQuestOrder = true;


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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackPayFragment = (CallBackPayFragment) getActivity();
    }

    private void initdata() {
        countDownTimer = new CountDownTimer(Constant.countDownTime, 1000) {
            @Override
            public void onTick(long l) {
                timeTv.setText(String.format(TIME_HINT, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                isQuestOrder = false;
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        }.start();
        bean = (OrderDetialBean) getArguments().getSerializable(Constant.KEY);
        if (bean != null) {
            payType = bean.getPayType();
            ImgGlideUtil.displayImage(bean.getGoodsImg(), img, true);
            nameTv.setText(bean.getGoodsName() + "");
            goodsNumTv.setText(String.format("共%d件商品", bean.getGoodsNum()));
            orderNoTv.setText(String.format("订单号%s", bean.getOrder_no()));
            priceTv.setText(String.format(Constant.PRICE_FORMAT, bean.getPrice() + ""));
            popPriceTv.setText(bean.getPrice()+"元");
            switchPayType();
        }

        codeEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    //处理事件
                    payingLayout.setVisibility(View.GONE);
                    payLayout.setVisibility(View.VISIBLE);
                    failLayyout.setVisibility(View.GONE);
                    String authCode = codeEt.getText() + "";
                    if (authCode.startsWith("1")) {//支付宝支付
                        mPresenter.scanPay(1, bean.getOrder_id(), authCode);
                    } else {
                        mPresenter.scanPay(2, bean.getOrder_id(), authCode);
                    }
                    codeEt.setText("");
                    return true;
                }
                return false;
            }
        });
    }


    private void switchPayType() {
        if (payType == ConfirmOrderActivity.ALI_PAY) {
            payMaxImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_max));
            minImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_min));
            payMaxTv.setText(R.string.ali_max_hint_str);
            minTv.setText(R.string.ali_min_hint_str);
            bottomLeftImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.alipay));
            bottomRightImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_can_icon));
        } else {
            payMaxImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.weichat_max));
            minImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_min));
            bottomLeftImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.weipay));
            bottomRightImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.weixin_scan_icon));

            payMaxTv.setText(R.string.wechat_max_hint_str);
            minTv.setText(R.string.ali_min_hint_str);
        }
    }

    /**
     * 支付成功
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        mPresenter.orderDetials(bean.getOrder_id());
    }

    /**
     * 支付失败
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
        if (orderDetialBean.getPaid_status() == 1) {
            callBackPayFragment.onCallBack(ConfirmOrderActivity.PAY_SUCC, orderDetialBean);
        } else if (isQuestOrder && orderDetialBean.getPaid_status() == 0) {
            mPresenter.orderDetials(bean.getOrder_id());
        }
    }

    @Override
    public void orderDetialFial(Throwable throwable) {
        mPresenter.orderDetials(bean.getOrder_id());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        countDownTimer.cancel();
    }

    @OnClick({R.id.min_img, R.id.min_tv, R.id.give_up_pay_tv, R.id.weichatpay_tv, R.id.alipay_tv, R.id.wechat_code_ll, R.id.ali_code_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.min_img:
            case R.id.min_tv:
                if (payType == ConfirmOrderActivity.ALI_PAY) {
                    payType = ConfirmOrderActivity.WECHAT_PAY;
                } else {
                    payType = ConfirmOrderActivity.ALI_PAY;
                }
                switchPayType();

                break;
            case R.id.give_up_pay_tv:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.weichatpay_tv:
                payLayout.setVisibility(View.VISIBLE);
                failLayyout.setVisibility(View.GONE);
                payingLayout.setVisibility(View.GONE);
                payType = ConfirmOrderActivity.WECHAT_PAY;
                switchPayType();
                countDownTimer.start();
                break;
            case R.id.alipay_tv:
                payLayout.setVisibility(View.VISIBLE);
                failLayyout.setVisibility(View.GONE);
                payingLayout.setVisibility(View.GONE);
                payType = ConfirmOrderActivity.ALI_PAY;
                switchPayType();
                countDownTimer.start();
                break;
            case R.id.wechat_code_ll:
                codeNameTv.setText(Constant.WEIXIN_CODE);
                countDownTimer.start();
                mPresenter.getPayCode(bean.getOrder_id(), 1);
                break;
            case R.id.ali_code_ll:
                codeNameTv.setText(Constant.WALI_CODE);
                countDownTimer.start();
                mPresenter.getPayCode(bean.getOrder_id(), 2);
                break;
            case R.id.confirm_payed_tv:
                //确认支付
                countDownTimer.start();
                mPresenter.orderDetials(bean.getOrder_id());
                break;
            case R.id.select_other_pay_tv:
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void getPayCode(String codeUrl) {
        popupWindow = Poputils.getPop(payView, R.layout.layout_submitorder, getActivity());
        try {
            codeImg.setImageBitmap(CodeUtils.createQRCode(codeUrl, 300));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
