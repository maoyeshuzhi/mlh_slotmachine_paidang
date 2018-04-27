package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/4/26.
 */

public class SubmitOrderBean {
    private int ship_type;//（-1：自提，0：快递，1：EMS，2：平邮）
    private int address_id;//收货地址id
    private int is_cart;//0-立即购买，1-购物车结算
    private String product_list;//商品列表json数据

    public int getShip_type() {
        return ship_type;
    }

    public void setShip_type(int ship_type) {
        this.ship_type = ship_type;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getIs_cart() {
        return is_cart;
    }

    public void setIs_cart(int is_cart) {
        this.is_cart = is_cart;
    }

    public String getProduct_list() {
        return product_list;
    }

    public void setProduct_list(String product_list) {
        this.product_list = product_list;
    }
}
