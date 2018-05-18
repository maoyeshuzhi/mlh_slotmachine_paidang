package com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment;


import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.DelivetyWayBean;
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

        return bean==null?0:bean.getArea_id();
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

        return  Double.valueOf(String.format("%.2f",price));
    }

    @Override
    public void submitOrder(SubmitOrderBean submitOrderBean,BaseObserver<BaseResult<OrderIdBean>> baseObserver) {
        mModel.submitOrder(submitOrderBean, baseObserver);

    }



    @Override
    public void orderDetials(final int orderId) {
        mModel.orderDetials(orderId, new BaseObserver<BaseResult<OrderDetialBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<OrderDetialBean> data) {
             if(data!=null){
                 mView.getOrderDetials(data.getData());
             }else {
                 orderDetials(orderId);
             }
            }

            @Override
            protected void onBaseError(Throwable t) {
                orderDetials(orderId);
            }
        });
    }

    /**
     *
     * @param list
     * @return  (0-不限制 1-自提 2-快递)
     */
    @Override
    public int getDeliveryType(List<GoodsBean> list) {
        boolean isPickUp = false;//是否存在自提
        boolean isExpress = false;//是否存在快递
        boolean  isNotLimite = false;//是否存在没有限制
        for (GoodsBean bean : list) {
              if(bean.getDelivery_type() ==1){
                  isPickUp = true;
              }else if(bean.getDelivery_type() ==2){
                isExpress = true;
            }else if(bean.getDelivery_type() ==0){
                  isNotLimite = true;
              }
        }

      if(isPickUp){
            return  1;
      }else if(!isPickUp){
          if(isExpress &&isNotLimite){
              return 0;
          }else if(isExpress &&!isNotLimite){
              return 2;
          }else {
              return 0;
          }
      }
        return 0;
    }

    /**
     * 查询可选择的取货方式
     * @param goodsIds
     */
    @Override
    public void deliveryWay(String goodsIds) {
        mModel.deliveryWay(goodsIds, new BaseObserver<BaseResult<List<DelivetyWayBean>>>(mView.getContext(),false) {
            @Override
            protected void onBaseNext(BaseResult<List<DelivetyWayBean>> data) {
                mView.getDeliveryType(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }
}
