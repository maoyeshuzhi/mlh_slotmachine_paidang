package com.maoye.mlh_slotmachine.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Rs on 2018/4/24.
 */

public class PayPopWindow extends PopupWindow {

   private ImageView dismissImg;
    private Context context;
    private View view;
    private TextView priceTv,weichatpayTv,wechetAliCodePayTv,alipayTv;

    public void setPrice( String price){
        priceTv.setText(String.format("%s元", price));
    }


    public PayPopWindow(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.pop_pay, null);
        priceTv = view.findViewById(R.id.price_tv);
        weichatpayTv = view.findViewById(R.id.weichatpay_tv);
        alipayTv = view.findViewById(R.id.alipay_tv);
        wechetAliCodePayTv = view.findViewById(R.id.wechet_ali_code_pay_tv);
        dismissImg = view.findViewById(R.id.dismiss_img);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
    }


    @OnClick({R.id.dismiss_img, R.id.weichatpay_tv, R.id.alipay_tv, R.id.wechet_ali_code_pay_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dismiss_img:
                dismiss();
                break;
            case R.id.weichatpay_tv:
                break;
            case R.id.alipay_tv:
                break;
            case R.id.wechet_ali_code_pay_tv:
                break;
        }
    }
}
