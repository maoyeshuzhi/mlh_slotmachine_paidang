package com.maoye.mlh_slotmachine.view.printreceiptactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment.ConfirmModel;


public class PrintreceiptPresenter extends BasePresenterImpl<PrintreceiptContract.View> implements PrintreceiptContract.Presenter {
    private ConfirmModel model;

    public PrintreceiptPresenter() {
        model = new ConfirmModel();
    }

    @Override
    public void OrderDetial(int orderId) {
        model.orderDetials(orderId, new BaseObserver<BaseResult<OrderDetialBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<OrderDetialBean> data) {
                mView.onSuccess(data);
            }

            @Override
            protected void onBaseError(Throwable t) {
                mView.onFail(t);
            }
        });
    }
}
