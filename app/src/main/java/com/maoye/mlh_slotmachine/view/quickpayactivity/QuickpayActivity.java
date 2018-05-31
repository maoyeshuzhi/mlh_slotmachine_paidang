package com.maoye.mlh_slotmachine.view.quickpayactivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.view.quickpayactivity.inputphonefragment.InputphoneFragment;
import com.maoye.mlh_slotmachine.view.quickpayactivity.quickpayorderfragment.QuickpayorderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 快付
 */
public class QuickpayActivity extends MVPBaseActivity<QuickpayContract.View, QuickpayPresenter> implements QuickpayContract.View ,InputphoneFragment.OnFragmentListener{

    @BindView(R.id.flow2_tv)
    TextView flow2Tv;
    @BindView(R.id.flow3_tv)
    TextView flow3Tv;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay);
        ButterKnife.bind(this);
        fragmentManager(R.id.fragment_container, new InputphoneFragment(), "inputPhoneFragment");
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @Override
    public void onCallBack(int type, ArrayList<QuickOrderBean> quickOrderBeanList) {
        switch (type){
            case 1:
                QuickpayorderFragment quickpayorderFragment = new QuickpayorderFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY,quickOrderBeanList);
                quickpayorderFragment.setArguments(bundle);
                fragmentManager(R.id.fragment_container, quickpayorderFragment, "quickPayorderFragment");
                break;
            case 2:
                break;

        }
    }
}
