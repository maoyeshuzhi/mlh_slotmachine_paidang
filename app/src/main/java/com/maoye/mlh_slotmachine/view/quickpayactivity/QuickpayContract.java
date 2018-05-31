package com.maoye.mlh_slotmachine.view.quickpayactivity;

import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class QuickpayContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getBannerData(int type);
        
    }
}
