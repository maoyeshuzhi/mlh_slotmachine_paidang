package com.maoye.mlh_slotmachine.mlh.login;


import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;


public class LoginContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
           void accountLogin(String mobile,String psw);
           void  mobileLogin(String mobile,int type);

    }
}
