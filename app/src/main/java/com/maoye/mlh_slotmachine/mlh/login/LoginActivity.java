package com.maoye.mlh_slotmachine.mlh.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.mlh.confirmorder.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.TextUtil;
import com.maoye.mlh_slotmachine.util.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.accountlogin_rb)
    RadioButton accountloginRb;
    @BindView(R.id.phonelogin_rb)
    RadioButton phoneloginRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.account_et)
    EditText accountEt;
    @BindView(R.id.account_view)
    View accountView;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw_view)
    View pswView;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.phone_view)
    View phoneView;
    @BindView(R.id.login_bt)
    Button loginBt;
    @BindView(R.id.back_imgbt)
    ImageButton backImgbt;
    @BindView(R.id.login_tv)
    TextView loginTv;
    private int loginType;
    public static final int ACCOUNT_LOIGN = 0;
    public static final int MOBILE_LOIGN = 1;//手机登录

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        rg.setOnCheckedChangeListener(this);
        accountEt.addTextChangedListener(accountTextWatcher);
        pswEt.addTextChangedListener(pswTextWatcher);
        phoneEt.addTextChangedListener(phoneTw);
    }

    TextWatcher phoneTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            isCanLogin(loginType == MOBILE_LOIGN && editable.length() > 0);
        }
    };
    TextWatcher accountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            isCanLogin(loginType == ACCOUNT_LOIGN && editable.length() > 0 && !TextUtils.isEmpty(pswEt.getText()));
        }
    };
    TextWatcher pswTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            isCanLogin(loginType == ACCOUNT_LOIGN && editable.length() > 0 && !TextUtils.isEmpty(accountEt.getText()));
        }
    };

    private void isCanLogin(boolean iscanlogin) {
        if (iscanlogin) {
            loginTv.setVisibility(View.GONE);
            loginBt.setVisibility(View.VISIBLE);
            loginBt.setClickable(true);
            loginBt.setEnabled(true);
        } else {
            loginBt.setVisibility(View.GONE);
            loginTv.setVisibility(View.VISIBLE);
            loginBt.setClickable(false);
            loginBt.setEnabled(false);
        }
    }


    @Override
    public void onSuccess(Object o) {
        openActivity(ConfirmOrderActivity.class);
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.login_bt, R.id.back_imgbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                if (islogin()) {
                    if (loginType == ACCOUNT_LOIGN)
                        mPresenter.accountLogin(accountEt.getText().toString(), pswEt.getText().toString());
                    if (loginType == MOBILE_LOIGN)
                        mPresenter.mobileLogin(phoneEt.getText().toString(), 2);
                }
                break;
            case R.id.back_imgbt:
                finish();
                break;
        }
    }

    private boolean islogin() {
   /*     if (loginType == MOBILE_LOIGN && !TextUtil.isMobileNo(phoneEt.getText() + "")) {
            Toast.getInstance().toast(this, Constant.PLEASE_INPUT_RIGHT_PHONE, 2);
            return false;
        }
        if (loginType == ACCOUNT_LOIGN && TextUtil.isMobileNo(accountEt.getText() + "")) {
            Toast.getInstance().toast(this, Constant.PLEASE_INPUT_RIGHT_PHONE, 2);
            return false;
        }*/
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.accountlogin_rb:
                loginType = ACCOUNT_LOIGN;
                switchLoginState(true);

                break;
            case R.id.phonelogin_rb:
                loginType = MOBILE_LOIGN;
                switchLoginState(false);
                break;
        }
    }

    private void switchLoginState(boolean isAccount) {
        if (isAccount) {
            accountloginRb.setTextColor(getResources().getColor(R.color.white));
            phoneloginRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            phoneEt.setVisibility(View.GONE);
            phoneView.setVisibility(View.GONE);
            accountEt.setVisibility(View.VISIBLE);
            accountView.setVisibility(View.VISIBLE);
            pswEt.setVisibility(View.VISIBLE);
            pswView.setVisibility(View.VISIBLE);
        } else {
            accountloginRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            phoneloginRb.setTextColor(getResources().getColor(R.color.white));
            phoneEt.setVisibility(View.VISIBLE);
            phoneView.setVisibility(View.VISIBLE);
            accountEt.setVisibility(View.GONE);
            accountView.setVisibility(View.GONE);
            pswEt.setVisibility(View.GONE);
            pswView.setVisibility(View.GONE);
        }
    }
}
