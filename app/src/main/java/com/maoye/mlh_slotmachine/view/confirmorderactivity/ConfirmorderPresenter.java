package com.maoye.mlh_slotmachine.view.confirmorderactivity;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.DelivetyWayBean;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment.ConfirmModel;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;

import java.util.List;


public class ConfirmorderPresenter extends BasePresenterImpl<ConfirmorderContract.View> implements ConfirmorderContract.Presenter {
    private ConfirmModel mModel;

    public ConfirmorderPresenter() {
        this.mModel = new ConfirmModel();
    }

    @Override
    public void markOrder(final int orderId) {
        mModel.markOrder(orderId, new BaseObserver<BaseResult>(mView.getContext(),false) {
            @Override
            protected void onBaseNext(BaseResult data) {

            }

            @Override
            protected void onBaseError(Throwable t) {
                markOrder(orderId);
            }
        });
    }
}
