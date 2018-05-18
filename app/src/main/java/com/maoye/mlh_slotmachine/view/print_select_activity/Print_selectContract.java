package com.maoye.mlh_slotmachine.view.print_select_activity;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class Print_selectContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void  getBannerData(int type);
        void statisticClickNum(int adverId);
    }
}
