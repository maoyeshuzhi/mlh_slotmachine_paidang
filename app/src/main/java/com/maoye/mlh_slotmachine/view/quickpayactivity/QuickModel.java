package com.maoye.mlh_slotmachine.view.quickpayactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/9.
 */

public class QuickModel extends BaseModel {

    public    void  getQuickOrder(String phone, BaseObserver<BaseResult<ArrayList<QuickOrderBean>>>observer){
     Observable observable = quickApiService.quickOrderList(phone);
     toSubscribe(observable,observer);

    }
}
