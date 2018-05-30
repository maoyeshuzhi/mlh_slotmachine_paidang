package com.maoye.mlh_slotmachine.view.mapguidesactivity;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class MapguidesContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void brandDetial(String id,int page,int limit,int type);

    }
}
