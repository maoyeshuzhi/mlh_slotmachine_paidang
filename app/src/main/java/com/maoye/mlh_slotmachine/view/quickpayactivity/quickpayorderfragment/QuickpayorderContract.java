package com.maoye.mlh_slotmachine.view.quickpayactivity.quickpayorderfragment;

import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.bean.QuickPayWXBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenter;
import com.maoye.mlh_slotmachine.mvp.BaseView;

import java.util.List;

public class QuickpayorderContract {
    interface View extends BaseView {
          void getWXCode(QuickPayWXBean wxBean);
          void getAliCode(QuickPayWXBean wxBean);
          void getWXCodeFial(Throwable t);
          void getAliCodeFial(Throwable t);
          void  queryBillResult(String jsonStr);
          void  quiryBillFail(Throwable t,boolean isQueryBill);
    }

    interface  Presenter extends BasePresenter<View> {
         void getWXCode(String saleNo,String price);
         void getAliCode(String saleNo,String price);
        QuickOrderBean getSelectOrder(List<QuickOrderBean>list);
        String getOrderPrice(QuickOrderBean selectOrderBean);
        void  billQuery( String saleNo);
        void  billQuery( String saleNo,boolean isQuery);
        void undataBill(String payJson);
    }
}
