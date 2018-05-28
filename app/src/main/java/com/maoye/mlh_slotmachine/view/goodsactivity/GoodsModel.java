package com.maoye.mlh_slotmachine.view.goodsactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Rs on 2018/5/21.
 */

public class GoodsModel extends BaseModel {

    public void defautlGoods(BaseObserver<BaseResult<List<GoodsBean>>> observer){
        Observable observable = mServletApi.defaultGoods();
        toSubscribe(observable,observer);

    }

    public void searchGoods(String name ,BaseObserver<BaseResult<BrandGoodsBean>> observer){
        Observable observable = mServletApi.searchGoods(name);
        toSubscribe(observable,observer);
    }

    public void brandGoods(int brandId ,BaseObserver<BaseResult<BrandGoodsBean>> observer){
        Observable observable = mServletApi.brandGoods(brandId);
        toSubscribe(observable,observer);
    }
}
