package com.maoye.mlh_slotmachine.view.searchgoodsactivity;

import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;


public class SearchgoodsContract {
    interface View extends BaseView {

        void  searchGoodsListData(BrandGoodsBean brandGoodsBean);
    }

    interface  Presenter extends BasePresenter<View> {
        void  defaultGoods();
        void searchGoods(String name);
        List<List<GoodsBean>> handerGoodsData(List<GoodsBean> listBeanXES);
    }
}
