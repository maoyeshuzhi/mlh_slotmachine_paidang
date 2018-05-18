package com.maoye.mlh_slotmachine.view.confirmorderactivity;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.DelivetyWayBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class ConfirmorderContract {
    interface View extends BaseView {

    }

    interface  Presenter extends BasePresenter<View> {
         void  markOrder(int orderId);

    }
}
