package com.maoye.mlh_slotmachine.view.quickprintactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class QuickprintContract {
    interface View extends BaseView {
        void  getSapId(String sapId);
        void getOrderDetials(BaseResult<QuickOrderDetialsBean> beanBaseResult);
        void getOrderDetialsFial(Throwable t);
    }

    interface  Presenter extends BasePresenter<View> {
        void  getBannerData(int type);
        void  orderData(String orderCode);
        void  getSapId(String deviceId);
        void statisticClickNum(int adverId);
    }
}
