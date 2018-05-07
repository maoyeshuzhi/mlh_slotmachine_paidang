package com.maoye.mlh_slotmachine.view.confirmorderactivity.succfragment;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class SuccContract {
    interface View extends BaseView {

    }

    interface  Presenter extends BasePresenter<View> {
        void  getBannerData(int type);
        void statisticClickNum(int adId);
    }
}
