package com.maoye.mlh_slotmachine.mlh.confirmorder.confirm;

import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class ConfirmContract {
    interface View extends BaseView {
          void getFreight(double fee);
    }

    interface  Presenter extends BasePresenter<View> {
        void  getAddressList(BaseObserver<BaseResult<AddressBean>> observer);
        void  getFeight(int area_id,int ship_type,String product_info);
        String productInfo(List<GoodsBean>list);
        int getArea_id(List<AddressBean.ListBean> list);
        int getAddress_id(List<AddressBean.ListBean> list);
        double getGoodsPrice(List<GoodsBean>list);
        void  submitOrder(SubmitOrderBean submitOrderBean,BaseObserver<BaseResult<OrderIdBean>> baseObserver);
    }
}
