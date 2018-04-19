package com.maoye.mlh_slotmachine.mlh.goodsdetials;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import io.reactivex.Observer;


public class GoodsdetialsContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void  getGoodsDetialsData(int id);
    }
}
