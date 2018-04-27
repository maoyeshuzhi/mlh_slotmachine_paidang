package com.maoye.mlh_slotmachine.mlh.confirmorder;


import com.maoye.mlh_slotmachine.mlh.confirmorder.confirm.ConfirmModel;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;


public class ConfirmorderPresenter extends BasePresenterImpl<ConfirmorderContract.View> implements ConfirmorderContract.Presenter {
    private ConfirmModel mModel;

    public ConfirmorderPresenter() {
        this.mModel = new ConfirmModel();
    }


}
