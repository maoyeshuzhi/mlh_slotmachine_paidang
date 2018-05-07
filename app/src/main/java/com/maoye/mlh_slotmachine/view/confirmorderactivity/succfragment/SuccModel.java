package com.maoye.mlh_slotmachine.view.confirmorderactivity.succfragment;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/2.
 */

public class SuccModel extends BaseModel {
    public void onClickAd(int adId, BaseObserver<BaseResult> observer) {
        Observable observable = mServletApi.statistic(adId);
        toSubscribe(observable, observer);
    }

    public  void bannarData(int type, BaseObserver<BaseResult<List<AdvertBean>>>observer){
        Observable observable = mServletApi.advert(type);
        toSubscribe(observable, observer);
    }
}
