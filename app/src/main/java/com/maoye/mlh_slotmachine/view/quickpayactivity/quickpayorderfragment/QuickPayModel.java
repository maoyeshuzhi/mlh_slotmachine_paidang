package com.maoye.mlh_slotmachine.view.quickpayactivity.quickpayorderfragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickPayWXBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/15.
 */

public class QuickPayModel extends BaseModel {

    public  void  getWXCode(String saleNo, String price, BaseObserver<BaseResult<QuickPayWXBean>> observer){
        Observable observable =quickApiService.quickWxPay(saleNo,price);
        toSubscribe(observable,observer);
    }

    public  void  getAliCode(String saleNo, String price, BaseObserver<BaseResult<QuickPayWXBean>> observer){
        Observable observable =quickApiService.quickAliPayCode(saleNo,price);
        toSubscribe(observable,observer);
    }

    public  void  billQuery(String saleNo , BaseObserver<BaseResult<String>> observer){
        Observable observable =quickApiService.billQuery(saleNo);
        toSubscribe(observable,observer);
    }

    public  void  updateBill(String payJson , BaseObserver<BaseResult<QuickPayWXBean>> observer){
        Observable observable =quickApiService.updateBill(payJson);
        toSubscribe(observable,observer);
    }
}
