package com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.PayCodeBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;


public class PayPresenter extends BasePresenterImpl<PayContract.View> implements PayContract.Presenter {
    private PayModel model;

    public PayPresenter() {
        model = new PayModel();
    }

    @Override
    public void scanPay(int payType, int orderId, String authCode) {
        model.scanPay(payType, orderId, authCode, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.onSuccess(data);
            }

            @Override
            protected void onBaseError(Throwable t) {
                mView.onFail(t);
            }
        });
    }

    @Override
    public void orderDetials(int orderId) {
        model.orderDetials(orderId, new BaseObserver<BaseResult<OrderDetialBean>>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult<OrderDetialBean> data) {
                mView.orderDetial(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                mView.orderDetialFial(t);
            }
        });
    }

    @Override
    public void getPayCode(int orderId, int payType) {
        model.getPayCode(orderId, payType, new BaseObserver<BaseResult<PayCodeBean>>(mView.getContext(), true) {
            @Override
            protected void onBaseNext(BaseResult<PayCodeBean> data) {
                mView.getPayCode(data.getData().getUrl());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    /**
     * 修改订单号
     *
     * @param orderId 原来订单号id
     */
    @Override
    public void changeOrderNo(int orderId, final boolean isClickWXCode) {
        model.changeOrderNo(orderId, new BaseObserver<BaseResult<OrderIdBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<OrderIdBean> data) {
                mView.getOrderInfo(data.getData(),isClickWXCode);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
