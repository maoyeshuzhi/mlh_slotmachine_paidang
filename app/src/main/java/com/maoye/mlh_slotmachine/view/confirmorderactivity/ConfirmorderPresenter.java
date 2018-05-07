package com.maoye.mlh_slotmachine.view.confirmorderactivity;


import com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment.ConfirmModel;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;


public class ConfirmorderPresenter extends BasePresenterImpl<ConfirmorderContract.View> implements ConfirmorderContract.Presenter {
    private ConfirmModel mModel;

    public ConfirmorderPresenter() {
        this.mModel = new ConfirmModel();
    }


}
