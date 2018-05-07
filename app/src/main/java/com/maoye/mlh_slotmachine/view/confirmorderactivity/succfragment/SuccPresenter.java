package com.maoye.mlh_slotmachine.view.confirmorderactivity.succfragment;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class SuccPresenter extends BasePresenterImpl<SuccContract.View> implements SuccContract.Presenter{
private SuccModel model;

    public SuccPresenter() {
        this.model = new SuccModel();
    }

    @Override
    public void getBannerData(int type) {
        model.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult<List<AdvertBean>> data) {
                mView.onSuccess(data.getData());
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
