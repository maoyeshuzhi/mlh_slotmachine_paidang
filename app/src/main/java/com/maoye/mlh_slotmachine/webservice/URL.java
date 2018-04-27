package com.maoye.mlh_slotmachine.webservice;


/**
 * Created by Rs on 2017/12/15.
 */

public interface URL {
    String VERSION_INFO = "home/machine/getVersion";
    String GOODS_DETIALS = "home/machine/goods";
    String HOME_DATA = "home/machine/index";
    String STATISTIC = "home/machine/advertClick";
    String ACCOUNT_LOGIN = "home/machine/login";
    String MOBILE_LOGIN = "home/machine/login";
    String ADD_CART = "home/machine/addCart";
    String CART_LIST = "home/machine/cartList";
    String PROVINCE = "home/machine/areaList";
    String ADD_ADDRESS = "home/machine/addAddress";
    String ADDRESS_LIST = "home/machine/getAddress";
    String CHANGE_GOODS_NUM = "home/machine/updateCart";
    String DELETE_CART_ALL = "home/machine/delCart";
    String FREIGHT = "home/m/getFreight";//运费计算
    String SUBMIT_ORDER = "home/machine/orderSubmit";
}
