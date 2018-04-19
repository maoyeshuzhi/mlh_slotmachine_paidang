package com.maoye.mlh_slotmachine.mlh.login;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/4/19.
 */

public class LoginModel extends BaseModel{

    public void accountLogin(String mobile,String psw ,BaseObserver<BaseResult> observer) {
         Observable observable =  mServletApi.accountLogin(mobile,psw).map(new HttpResultFunc());
        toSubscribe(observable, observer);
    }

    public void mobileLogin(String mobile,int type ,BaseObserver<BaseResult> observer) {
         Observable observable =  mServletApi.mobileLogin(mobile,type).map(new HttpResultFunc());
         toSubscribe(observable, observer);
    }
}
