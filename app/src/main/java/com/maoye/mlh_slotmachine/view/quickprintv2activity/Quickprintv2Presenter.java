package com.maoye.mlh_slotmachine.view.quickprintv2activity;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.quickprintactivity.QuickPayPrintModel;

import java.util.List;


public class Quickprintv2Presenter extends BasePresenterImpl<Quickprintv2Contract.View> implements Quickprintv2Contract.Presenter {
    private QuickPayPrintModel model;

    public Quickprintv2Presenter() {
        model = new QuickPayPrintModel();
    }

    @Override
    public void markPrint(String saleNo) {
        model.markPrint(saleNo, new BaseObserver<BaseResult>(MyContext.appContext,false) {
            @Override
            protected void onBaseNext(BaseResult data) {

            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void orderData(String saleNo) {
        model.orderData(saleNo, new BaseObserver<BaseResult<QuickOrderDetialsBean>>(mView.getContext(), true) {
            @Override
            protected void onBaseNext(BaseResult<QuickOrderDetialsBean> data) {
                if (mView != null ) mView.getOrderDetials(data);
            }

            @Override
            protected void onBaseError(Throwable t) {
                if (mView != null) mView.getOrderDetialsFial(t);
            }
        });
    }

    @Override
    public void getBannerData(int type) {
        model.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext()) {
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
    public void statisticClickNum(int adId) {
        model.onClickAd(adId, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
