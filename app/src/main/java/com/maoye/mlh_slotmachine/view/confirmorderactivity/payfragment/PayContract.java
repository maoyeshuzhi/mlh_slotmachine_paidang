package com.maoye.mlh_slotmachine.view.confirmorderactivity.payfragment;

import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;



public class PayContract {
    interface View extends BaseView {
        void orderDetial(OrderDetialBean orderDetialBeant);
        void orderDetialFial(Throwable throwable);
        void getPayCode(String codeUrl);
        void  getOrderInfo(OrderIdBean orderIdBean,boolean isClickWXCode);
    }

    interface  Presenter extends BasePresenter<View> {
        void  scanPay(int payType,int orderId,String authCode);
        void orderDetials(int orderId);
        void getPayCode(int orderId, int payType);
        void changeOrderNo(int orderId,boolean isClickWXCode);
    }
}
