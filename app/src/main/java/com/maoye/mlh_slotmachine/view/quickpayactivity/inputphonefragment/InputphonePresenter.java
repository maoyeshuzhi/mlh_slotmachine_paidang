package com.maoye.mlh_slotmachine.view.quickpayactivity.inputphonefragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.quickpayactivity.QuickModel;

import java.util.ArrayList;


public class InputphonePresenter extends BasePresenterImpl<InputphoneContract.View> implements InputphoneContract.Presenter {
    private QuickModel model;

    public InputphonePresenter() {
        model = new QuickModel();
    }

    @Override
    public void getOrderList(String phone) {
        model.getQuickOrder(phone, new BaseObserver<BaseResult<ArrayList<QuickOrderBean>>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<ArrayList<QuickOrderBean>> data) {
                mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
