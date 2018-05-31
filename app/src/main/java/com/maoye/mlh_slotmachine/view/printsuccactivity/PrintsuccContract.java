package com.maoye.mlh_slotmachine.view.printsuccactivity;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class PrintsuccContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
      void   getBannerData(int type);
    }
}
