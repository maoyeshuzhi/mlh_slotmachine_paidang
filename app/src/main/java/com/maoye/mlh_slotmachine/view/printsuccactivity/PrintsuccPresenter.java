package com.maoye.mlh_slotmachine.view.printsuccactivity;

import android.os.CountDownTimer;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.quickprintactivity.QuickPayPrintModel;

import java.util.List;


public class PrintsuccPresenter extends BasePresenterImpl<PrintsuccContract.View> implements PrintsuccContract.Presenter {
    private QuickPayPrintModel model;


    public PrintsuccPresenter() {
        model = new QuickPayPrintModel();
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
}
