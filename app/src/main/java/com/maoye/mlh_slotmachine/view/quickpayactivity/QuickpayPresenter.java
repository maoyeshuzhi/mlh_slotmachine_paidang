package com.maoye.mlh_slotmachine.view.quickpayactivity;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.loginactivity.LoginModel;

import java.util.List;


public class QuickpayPresenter extends BasePresenterImpl<QuickpayContract.View> implements QuickpayContract.Presenter{
   private LoginModel model;
    public QuickpayPresenter() {
        model = new LoginModel();
    }

    @Override
    public void getBannerData(int type) {
        model.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<List<AdvertBean>> data) {
                if(data!=null){
                    mView.onSuccess(data.getData());
                }
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
