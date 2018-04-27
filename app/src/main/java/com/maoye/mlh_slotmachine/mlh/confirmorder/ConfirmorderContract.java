package com.maoye.mlh_slotmachine.mlh.confirmorder;


import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;


public class ConfirmorderContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {

    }
}
