package com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment;

import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.FeeBean;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.PayCodeBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.mvp.BaseModel;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;

/**
 * Created by Rs on 2018/4/24.
 */

public class ConfirmModel extends BaseModel {

    public   void getAddressList(BaseObserver<BaseResult<AddressBean>> baseObserver){
        Observable observable = mServletApi.addressList().map(new HttpResultFunc<AddressBean>());
        toSubscribe(observable,baseObserver);
    }

    public void getFreight(int area_id,int ship_type,String product_info,BaseObserver<BaseResult<FeeBean>> baseObserver){
        Observable observable = mServletApi.freight(area_id,ship_type,product_info).map(new HttpResultFunc<FeeBean>());
        toSubscribe(observable,baseObserver);
    }

    public void submitOrder(SubmitOrderBean submitOrder, BaseObserver<BaseResult<OrderIdBean>> baseObserver){
        Observable observable = mServletApi.submitOrder(submitOrder).map(new HttpResultFunc<OrderIdBean>());
        toSubscribe(observable,baseObserver);
    }

    public void getPayCode(int orderId,int payType, BaseObserver<BaseResult<PayCodeBean>> baseObserver){
        Observable observable = mServletApi.pay_code(payType,orderId).map(new HttpResultFunc<PayCodeBean>());
        toSubscribe(observable,baseObserver);
    }

    public void orderDetials(int orderId, BaseObserver<BaseResult<OrderDetialBean>> baseObserver){
        Observable observable = mServletApi.orderDetials(orderId).map(new HttpResultFunc<OrderDetialBean>());
        toSubscribe(observable,baseObserver);
    }

}
