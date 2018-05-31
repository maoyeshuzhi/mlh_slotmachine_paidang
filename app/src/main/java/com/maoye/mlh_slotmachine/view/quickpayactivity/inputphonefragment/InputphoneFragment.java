package com.maoye.mlh_slotmachine.view.quickpayactivity.inputphonefragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.TextUtil;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment.ConfirmFragment;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.view.printreceiptactivity.PrintReceiptActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 输入手机号码界面
 */
public class InputphoneFragment extends MVPBaseFragment<InputphoneContract.View, InputphonePresenter> implements InputphoneContract.View {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.confirm_bt)
    Button confirBt;
    @BindView(R.id.goshop_bt)
    Button goshopBt;
    @BindView(R.id.back_bt)
    ImageView backBt;
    @BindView(R.id.time_tv)
    TextView timeTv;
    Unbinder unbinder;
    @BindView(R.id.print_bill_bt)
    Button printBillBt;
    private OnFragmentListener onFragmentListener;

    public interface OnFragmentListener {
        void onCallBack(int type, ArrayList<QuickOrderBean> quickOrderBeanList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentListener = (OnFragmentListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inputphone, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSuccess(Object o) {
        ArrayList<QuickOrderBean> quickOrderBeanList= (ArrayList<QuickOrderBean>) o;
        if(quickOrderBeanList==null||quickOrderBeanList.size()==0){
             Toast.getInstance().toast(getContext(),"该手机号暂无订单数据",2);
        }else {
            if (onFragmentListener != null) onFragmentListener.onCallBack(1, quickOrderBeanList);
        }

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.print_bill_bt, R.id.confirm_bt, R.id.goshop_bt, R.id.back_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                if (!TextUtils.isEmpty(phoneEt.getText()) && TextUtil.isMobile(phoneEt.getText() + "")) {
                       mPresenter.getOrderList(phoneEt.getText().toString());
                } else {
                    Toast.getInstance().toast(getContext(), Constant.PLEASE_INPUT_RIGHT_PHONE, 2);
                }
                break;
            case R.id.print_bill_bt:
                Intent payIntent = new Intent(getActivity(), PrintReceiptActivity.class);
                payIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(payIntent);
                break;
            case R.id.goshop_bt:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.back_bt:
                getActivity().finish();
                break;
        }
    }
}
