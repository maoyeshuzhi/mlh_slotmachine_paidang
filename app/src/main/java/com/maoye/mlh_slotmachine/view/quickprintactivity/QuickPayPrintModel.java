package com.maoye.mlh_slotmachine.view.quickprintactivity;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.bean.SapIdBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/16.
 */

public class QuickPayPrintModel extends BaseModel {

    public  void  orderData(String saleNo, BaseObserver<BaseResult<QuickOrderDetialsBean>>observer){
          Observable observable = quickApiService.quickOrderData(saleNo);
          toSubscribe(observable,observer);
    }

    public void getSapId(String deviceId, BaseObserver<BaseResult<SapIdBean>> observer){
        Observable observable = mServletApi.querySapId();
        toSubscribe(observable,observer);
    }
    public  void bannarData(int type, BaseObserver<BaseResult<List<AdvertBean>>>observer){
        Observable observable = mServletApi.advert(type);
        toSubscribe(observable, observer);
    }
}
