package com.maoye.mlh_slotmachine.view.quickpayactivity.quickpayorderfragment;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.bean.QuickPayWXBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;


public class QuickpayorderPresenter extends BasePresenterImpl<QuickpayorderContract.View> implements QuickpayorderContract.Presenter {

    private QuickPayModel model;

    public QuickpayorderPresenter() {
        model = new QuickPayModel();
    }

    @Override
    public void getWXCode(String saleNo, String price) {
        model.getWXCode(saleNo, price, new BaseObserver<BaseResult<QuickPayWXBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<QuickPayWXBean> data) {
                if(data!=null) mView.getWXCode(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                   mView.getWXCodeFial(t);
            }
        });
    }

    @Override
    public void getAliCode(String saleNo, String price) {
          model.getAliCode(saleNo, price, new BaseObserver<BaseResult<QuickPayWXBean>>(mView.getContext(),false) {
              @Override
              protected void onBaseNext(BaseResult<QuickPayWXBean> data) {
                  if(data!=null)mView.getAliCode(data.getData());
              }

              @Override
              protected void onBaseError(Throwable t) {
                     mView.getAliCodeFial(t);
              }
          });
    }

    @Override
    public QuickOrderBean getSelectOrder(List<QuickOrderBean> list) {
        QuickOrderBean bean = null;
        for (QuickOrderBean quickOrderBean : list) {
            if(quickOrderBean.isSelect())bean = quickOrderBean;
        }
        return bean;
    }

    @Override
    public String getOrderPrice(QuickOrderBean selectOrderBean) {
          double price = 0.00;
        for (QuickOrderBean.SaledListBean saledListBean : selectOrderBean.getSaledList()) {
            price = price + saledListBean.getSaleAmount()-saledListBean.getCouponAmount();
        }
        return String.format("%.2f",price);
    }

    @Override
    public void billQuery(String saleNo) {

    }

    /**
     * 轮询
     * @param saleNo
     * @param isQuery true表示轮询
     */
    @Override
    public void billQuery(String saleNo, boolean isQuery) {
        model.billQuery(saleNo, new BaseObserver<BaseResult<String>>(mView.getContext(),isQuery?false:true) {
            @Override
            protected void onBaseNext(BaseResult<String> data) {

            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void undataBill(String payJson) {

    }
}
