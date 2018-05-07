package com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment;


import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.FeeBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.PayCodeBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfirmPresenter extends BasePresenterImpl<ConfirmContract.View> implements ConfirmContract.Presenter {
    private ConfirmModel mModel;

    public ConfirmPresenter() {
        this.mModel = new ConfirmModel();
    }

    @Override
    public void getAddressList(BaseObserver<BaseResult<AddressBean>> observer) {
        mModel.getAddressList(observer);
    }

    @Override
    public void getFeight(int area_id, int ship_type, String product_info) {
        mModel.getFreight(area_id, ship_type, product_info, new BaseObserver<BaseResult<FeeBean>>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult<FeeBean> data) {

                mView.getFreight(data.getData().getFee());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }


    @Override
    public String productInfo(List<GoodsBean> list) {
        List<Map<String,Integer>> resultList = new ArrayList<>();
        for (GoodsBean goodsBean : list) {
            Map<String,Integer> params = new HashMap<>();
            params.put("id",goodsBean.getProduct_id());
            params.put("num",goodsBean.getNum());
            resultList.add(params);
        }
        return  new Gson().toJson(resultList);
    }

    @Override
    public int getArea_id(List<AddressBean.ListBean> list) {
        AddressBean.ListBean bean = null;
        for (AddressBean.ListBean addressBean : list) {
            if(addressBean.getDefaultX()==1){
                bean = addressBean;
            }
        }

        return bean.getArea_id();
    }

    @Override
    public int getAddress_id(List<AddressBean.ListBean> list) {
        AddressBean.ListBean bean = null;
        for (AddressBean.ListBean addressBean : list) {
            if(addressBean.getDefaultX()==1){
                bean = addressBean;
            }
        }
        return bean == null? 0 : bean.getId();
    }

    @Override
    public double getGoodsPrice(List<GoodsBean> list) {
        double price = 0.00;
        for (GoodsBean bean : list) {
            price = price+Double.valueOf(bean.getPrice()+"")*bean.getNum();
        }

        return price;
    }

    @Override
    public void submitOrder(SubmitOrderBean submitOrderBean,BaseObserver<BaseResult<OrderIdBean>> baseObserver) {
        mModel.submitOrder(submitOrderBean, baseObserver);

    }

    @Override
    public void getPayCode(int orderId, int payType) {
        mModel.getPayCode(orderId, payType, new BaseObserver<BaseResult<PayCodeBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<PayCodeBean> data) {
                   mView.getPayCode(data.getData().getUrl());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void orderDetials(int orderId) {
        mModel.orderDetials(orderId, new BaseObserver<BaseResult<OrderDetialBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<OrderDetialBean> data) {
                  mView.paySucc(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                 mView.payFail(t);
            }
        });
    }

}
