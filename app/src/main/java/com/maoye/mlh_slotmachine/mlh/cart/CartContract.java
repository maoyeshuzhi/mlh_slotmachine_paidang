package com.maoye.mlh_slotmachine.mlh.cart;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;


public class CartContract {
    interface View extends BaseView {
       void  getChangeGoodsNumResult(BaseResult baseResult,int postion,int goodsNum);
       void  deleteAllResult(BaseResult baseResult);
       void  deleteCartResult(BaseResult baseResult);
    }

    interface  Presenter extends BasePresenter<View> {
         void  getCartGoods();
         void changeGoodsNum(int index,int num);
         void  deleteAll();
         void  deleteCart(String indexs);
         String getSelectPotion(List<GoodsBean> list);
         String getPrice(List<GoodsBean> list);
    }
}
