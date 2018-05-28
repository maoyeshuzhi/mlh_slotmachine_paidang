package com.maoye.mlh_slotmachine.view.goodsactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class GoodsContract {
    interface View extends BaseView {

        
    }

    interface  Presenter extends BasePresenter<View> {
        void goodsListData(int brandId);
        List<List<GoodsBean>> handerGoodsData(List<BrandGoodsBean.ListBean> listBeanXES);
    }
}
