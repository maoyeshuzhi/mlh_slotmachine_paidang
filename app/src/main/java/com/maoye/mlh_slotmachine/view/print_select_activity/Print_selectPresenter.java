package com.maoye.mlh_slotmachine.view.print_select_activity;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeModel;
import com.maoye.mlh_slotmachine.view.loginactivity.LoginModel;

import java.util.List;


public class Print_selectPresenter extends BasePresenterImpl<Print_selectContract.View> implements Print_selectContract.Presenter {
    private LoginModel model;
    private HomeModel homeModel;


    public Print_selectPresenter() {
        model = new LoginModel();
    }

    @Override
    public void getBannerData(int type) {
        model.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext(), true) {
            @Override
            protected void onBaseNext(BaseResult<List<AdvertBean>> data) {
                if (data != null) mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void statisticClickNum(int adverId) {
           if(homeModel==null){
               homeModel = new HomeModel();
           }
        homeModel.onClickAd(adverId, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
