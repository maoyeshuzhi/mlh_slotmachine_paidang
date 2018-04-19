package com.maoye.mlh_slotmachine.mlh.goodsdetials;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Rs on 2018/4/16.
 */

public class GoodsDetialsModel extends BaseModel{
    public  void questGoodsDetialsData(int goodsId, Observer<BaseResult<GoodsDetialsBean>>observer){
        Observable observable = mServletApi.goodsDetials(goodsId).map(new HttpResultFunc<GoodsDetialsBean>());
        toSubscribe(observable,observer);

    }
}
