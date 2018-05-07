package com.maoye.mlh_slotmachine.view.loginactivity;


import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;


public class LoginContract {
    interface View extends BaseView {
        void getBannerData(List<AdvertBean>list);
    }

    interface  Presenter extends BasePresenter<View> {
           void accountLogin(String mobile,String psw);
           void  mobileLogin(String mobile,int type);
           void statisticClickNum(int adId);
           void  getBannerData(int type);
    }
}
