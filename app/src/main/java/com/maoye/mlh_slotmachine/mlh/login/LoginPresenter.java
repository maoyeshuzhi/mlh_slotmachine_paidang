package com.maoye.mlh_slotmachine.mlh.login;



import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;


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
    public void mobileLogin(String mobile, int type) {
        loginModel.mobileLogin(mobile, type, new BaseObserver<BaseResult>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult data) {
                  mView.onSuccess(data);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

}
