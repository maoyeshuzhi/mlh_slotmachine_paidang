package com.maoye.mlh_slotmachine.mlh.cart;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/4/25.
 */

public class CartModel extends BaseModel {

    public void getCartGoodsList(BaseObserver<BaseResult<List<GoodsBean>>> observer){
        Observable observable = mServletApi.cartlist().map(new HttpResultFunc());
        toSubscribe(observable,observer);
    }

    public void changeGoodsNum(int index,int num ,BaseObserver<BaseResult> observer){
        Observable observable = mServletApi.changeGoodsNum(index,num).map(new HttpResultFunc());
        toSubscribe(observable,observer);
    }

    public void deleteAll(BaseObserver<BaseResult> observer){
        Observable observable = mServletApi.deleteCartAll(2).map(new HttpResultFunc());
        toSubscribe(observable,observer);
    }

    public void deleteCart(String indexs,BaseObserver<BaseResult> observer){
        Observable observable = mServletApi.deleteCart(indexs).map(new HttpResultFunc());
        toSubscribe(observable,observer);
    }

}
