package com.maoye.mlh_slotmachine.mlh.goodsdetials;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;


public class GoodsdetialsContract {
    interface View extends BaseView {
        void addCardSucc(BaseResult baseResult);
    }

    interface  Presenter extends BasePresenter<View> {
        void  getGoodsDetialsData(int id);
        void  addCart(String deviceNo,int id,int specId,int num);
    }
}
