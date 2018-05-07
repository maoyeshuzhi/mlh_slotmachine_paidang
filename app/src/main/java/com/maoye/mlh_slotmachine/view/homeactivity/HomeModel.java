package com.maoye.mlh_slotmachine.view.homeactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.VersionInfoBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/4/11.
 */

public class HomeModel extends BaseModel {

    public void getHomeData( BaseObserver<BaseResult<HomeBean>> observer) {
        Observable observable = mServletApi.homedata().map(new HttpResultFunc<HomeBean>());
        toSubscribe(observable, observer);
    }

    public void onClickAd(int adId, BaseObserver<BaseResult> observer) {
        Observable observable = mServletApi.statistic(adId);
        toSubscribe(observable, observer);
    }

    public void versionInfo(BaseObserver<BaseResult<VersionInfoBean>> observer){
        Observable observable = mServletApi.versionInfo();
        toSubscribe(observable, observer);
    }


}
