package com.maoye.mlh_slotmachine.view.quickprintactivity;

import android.text.TextUtils;

import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.bean.SapIdBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeModel;

import java.util.List;


public class QuickprintPresenter extends BasePresenterImpl<QuickprintContract.View> implements QuickprintContract.Presenter {
    private QuickPayPrintModel model;
    private HomeModel homeModel;

    public QuickprintPresenter() {
        model = new QuickPayPrintModel();
    }

    @Override
    public void getBannerData(int type) {
        model.bannarData(type, new BaseObserver<BaseResult<List<AdvertBean>>>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult<List<AdvertBean>> data) {
                if (data != null) {
                    mView.onSuccess(data.getData());
                }
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void statisticClickNum(int adverId) {
        if (homeModel == null) {
            homeModel = new HomeModel();
        }
        homeModel.onClickAd(adverId, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void markPrint(String saleNo) {
        model.markPrint(saleNo, new BaseObserver<BaseResult>(mView.getContext(),false) {
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
    public void getSapId(final String deviceId) {
        if(mView!=null) {
            model.getSapId(deviceId, new BaseObserver<BaseResult<SapIdBean>>(mView.getContext(), false) {
                @Override
                protected void onBaseNext(BaseResult<SapIdBean> data) {
                    if (data != null && data.getData() != null && !TextUtils.isEmpty(data.getData().getSap_id())) {
                        mView.getSapId(data.getData().getSap_id());
                    }
                }

                @Override
                protected void onBaseError(Throwable t) {
                    getSapId(deviceId);
                }
            });
        }
    }
}
