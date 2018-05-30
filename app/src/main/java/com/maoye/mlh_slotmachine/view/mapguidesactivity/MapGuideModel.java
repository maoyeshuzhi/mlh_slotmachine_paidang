package com.maoye.mlh_slotmachine.view.mapguidesactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsListBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/5/28.
 */

public class MapGuideModel extends BaseModel {

    public void brandDetials(String id, int page, int limmit, BaseObserver<BaseResult<BrandGoodsListBean>> observer){
          Observable observable = mServletApi.brandDetial(id,page,limmit);
          toSubscribe(observable,observer);

}

}
