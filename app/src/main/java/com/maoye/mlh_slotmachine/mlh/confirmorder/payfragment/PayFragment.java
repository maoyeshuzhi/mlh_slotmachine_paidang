package com.maoye.mlh_slotmachine.mlh.confirmorder.payfragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.event.GoodsEventBean;
import com.maoye.mlh_slotmachine.mlh.confirmorder.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.Constant;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PayFragment extends MVPBaseFragment<PayContract.View, PayPresenter> implements PayContract.View {

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
    private int payType;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pay, null);
        unbinder = ButterKnife.bind(this, view);
       //EventBus.getDefault().register(this);
        initdata();
        return view;
    }

    private void initdata() {
        GoodsEventBean goodsEventBean = (GoodsEventBean) getArguments().getSerializable(Constant.KEY);
        if (goodsEventBean != null) {
            payType = goodsEventBean.getPayType();
            switchPayType();
        }
    }
/*

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoodsEventBean goodsEventBean) {

    }
*/

    private void switchPayType() {
        if (payType == ConfirmOrderActivity.ALI_PAY) {
            payMaxImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_max));
            minImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_min));
            payMaxTv.setText(R.string.ali_max_hint_str);
            minTv.setText(R.string.ali_min_hint_str);
        } else {
            payMaxImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.weichat_max));
            minImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ali_min));
            payMaxTv.setText(R.string.wechat_max_hint_str);
            minTv.setText(R.string.ali_min_hint_str);
        }
    }



    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.min_img, R.id.min_tv, R.id.give_up_pay_tv})
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
                break;
        }
    }
}
