package com.maoye.mlh_slotmachine.view.printreceiptactivity;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class PrintreceiptContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
       void   OrderDetial(int orderId);
       void markOrder(int orderId);
        
    }
}
