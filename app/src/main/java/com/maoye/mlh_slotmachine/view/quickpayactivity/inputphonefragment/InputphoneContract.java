package com.maoye.mlh_slotmachine.view.quickpayactivity.inputphonefragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;


public class InputphoneContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getOrderList(String phone);
    }
}
