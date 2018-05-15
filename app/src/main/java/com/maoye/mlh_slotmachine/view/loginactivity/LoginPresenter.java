package com.maoye.mlh_slotmachine.view.loginactivity;



import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{
private LoginModel loginModel;
public LoginPresenter(){
    this.loginModel = new LoginModel();
}
    @Override
    public void accountLogin(String mobile, String psw) {
     loginModel.accountLogin(mobile, psw, new BaseObserver<BaseResult>(mView.getContext()) {
       @Override
       protected void onBaseNext(BaseResult data) {
             mView.onSuccess(data);
       }

       @Override
       protected void onBaseError(Throwable t) {

       }
   });
    }

    @Override
    public void mobileLogin(String mobile, int type,String code) {
        loginModel.mobileLogin(mobile, type,code, new BaseObserver<BaseResult>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult data) {
                  mView.onSuccess(data);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void statisticClickNum(int adId) {
        loginModel.onClickAd(adId, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void getBannerData(int type) {
        loginModel.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult<List<AdvertBean>> data) {
                mView.getBannerData(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void getVerrifiCode(String phone) {
        loginModel.getVerifiCode(phone, new BaseObserver<BaseResult>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.getVerifiCode();
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

}
