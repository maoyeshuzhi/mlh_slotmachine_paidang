package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/4/26.
 */

public class OrderIdBean {
    private String order_no;
    private int order_id;
    private String sap_id;
    private String order_amount;

    public String getSap_id() {
        return sap_id;
    }

    public void setSap_id(String sap_id) {
        this.sap_id = sap_id;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
