package com.maoye.mlh_slotmachine.bean;

import java.io.Serializable;
import java.util.List;


public class OrderDetialBean implements Serializable{
    private int payType;//支付类型 1微信，2支付宝
    private String goodsImg;
    private String goodsName;
    private int goodsNum;//商品件数
    private String price;//金额



    private int order_id;
    private String order_no;
    private int paid_status;//支付状态(0-未支付，1-已支付)
    private String paid_time;//支付时间
    private String logistics_link;//查看物流扫码地址
    private String mobile;
    private String created_at;//下单时间
    private double order_amount;//总原价
    private String discount_amount;//总优惠
    private String paid_amount;//实付款
    private List<ProductListBean> product_list;
    private String out_trade_no;//流水号
    private String get_points_total;//本单所获积分
    private String sap_id;//门店storeId
    private String freight_amount;//邮费
    private int payment_type;//支付方式(1-微信 2-支付宝)

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getFreight_amount() {
        return freight_amount;
    }

    public void setFreight_amount(String freight_amount) {
        this.freight_amount = freight_amount;
    }

    public String getSap_id() {
        return sap_id;
    }

    public void setSap_id(String sap_id) {
        this.sap_id = sap_id;
    }

    public String getGet_points_total() {
        return get_points_total;
    }

    public void setGet_points_total(String get_points_total) {
        this.get_points_total = get_points_total;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(int paid_status) {
        this.paid_status = paid_status;
    }

    public String getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(String paid_time) {
        this.paid_time = paid_time;
    }

    public String getLogistics_link() {
        return logistics_link;
    }

    public void setLogistics_link(String logistics_link) {
        this.logistics_link = logistics_link;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public List<ProductListBean> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductListBean> product_list) {
        this.product_list = product_list;
    }

    public static class ProductListBean implements Serializable{

        private int product_id;
        private String product_name;
        private String product_image;
        private String spec_vals;
        private String price;
        private int num;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
        }

        public String getSpec_vals() {
            return spec_vals;
        }

        public void setSpec_vals(String spec_vals) {
            this.spec_vals = spec_vals;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
