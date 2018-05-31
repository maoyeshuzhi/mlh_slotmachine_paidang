package com.maoye.mlh_slotmachine.view.quickprintv2activity;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;


public class Quickprintv2Contract {
    interface View extends BaseView {
        void getOrderDetials(BaseResult<QuickOrderDetialsBean> beanBaseResult);
        void getOrderDetialsFial(Throwable t);
        void getBannerData(List<AdvertBean> list);
    }

    interface  Presenter extends BasePresenter<View> {
        void markPrint(String saleNo);
        void  orderData(String orderCode);
        void  getBannerData(int type);
        void statisticClickNum(int adId);
    }
}
