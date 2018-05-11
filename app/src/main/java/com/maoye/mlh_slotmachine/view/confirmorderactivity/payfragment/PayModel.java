package com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.PayCodeBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/3.
 */

public class PayModel extends BaseModel {
    public void scanPay(int payType, int orderId, String authCode, BaseObserver<BaseResult> observer){
        Observable<BaseResult> observable = mServletApi.scanPay(payType,orderId,authCode);
        toSubscribe(observable,observer);
    }

    public void orderDetials(int orderId, BaseObserver<BaseResult<OrderDetialBean>> baseObserver){
        Observable observable = mServletApi.orderDetials(orderId).map(new HttpResultFunc<OrderDetialBean>());
        toSubscribe(observable,baseObserver);
    }
    public void getPayCode(int orderId,int payType, BaseObserver<BaseResult<PayCodeBean>> baseObserver){
        Observable observable = mServletApi.pay_code(payType,orderId).map(new HttpResultFunc<PayCodeBean>());
        toSubscribe(observable,baseObserver);
    }


    public void changeOrderNo(int orderId ,BaseObserver<BaseResult<OrderIdBean>> observer){
        Observable observable = mServletApi.changeOrderNo(orderId).map(new HttpResultFunc<OrderIdBean>());
        toSubscribe(observable,observer);
    }
}
