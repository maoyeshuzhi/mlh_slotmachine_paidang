package com.maoye.mlh_slotmachine.view.goodsdetialsactivity;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;


public class GoodsdetialsContract {
    interface View extends BaseView {
        void addCardSucc(BaseResult baseResult);
    }

    interface  Presenter extends BasePresenter<View> {
        void  getGoodsDetialsData(int id );
        void  addCart(int id,int specId,int num);
        int getSpecId(List<GoodsDetialsBean.SpecListBean> spec_list);
        String formatTime(long ms);
        String getSpec_vals(List<GoodsDetialsBean.SpecListBean> spec_list);
        String getPrice(List<GoodsDetialsBean.SpecListBean> spec_list);
    }
}
