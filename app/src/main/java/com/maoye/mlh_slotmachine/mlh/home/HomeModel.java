package com.maoye.mlh_slotmachine.mlh.home;

import android.content.Context;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/4/11.
 */

public class HomeModel extends BaseModel {

    public void getHomeData(String deviceNo, BaseObserver<BaseResult<HomeBean>> observer) {
        Observable observable = mServletApi.homedata(deviceNo).map(new HttpResultFunc<HomeBean>());
        toSubscribe(observable, observer);
    }

    public void onClickAd(int adId, BaseObserver<BaseResult> observer) {
        Observable observable = mServletApi.statistic(adId);
        toSubscribe(observable, observer);
    }

}
