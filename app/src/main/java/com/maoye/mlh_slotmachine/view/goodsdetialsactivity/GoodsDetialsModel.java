package com.maoye.mlh_slotmachine.view.goodsdetialsactivity;

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

    public void addCart(int id,int specId,int num ,Observer<BaseResult>observer){
        Observable observable = mServletApi.addCart(id,specId,num).map(new HttpResultFunc());
        toSubscribe(observable,observer);

    }
}
