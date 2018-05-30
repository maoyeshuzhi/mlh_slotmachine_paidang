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
    String ORDER_DETIALS = "home/machine/orderGet";//订单详情
    String ADVERT = "home/machine/getAdvert";
    String PRINTER_STATUS = "home/machine/updateMachine";//打印机,设备状态
    String PAY_CODE = "home/machine/get-qrcode-pay-url";
    String ORDER_DETIAL_H5 = "user/order-info?id=";//h5订单详情接口
    String BILL = "jfinal-order/invoice?parmId=99&type=true&msg=";
    String VIP_CN = "http://weixin.qq.com/r/WzilvZrE2selrd7e921J";
    String SCAN_PAY = "home/machine/goto-micro-pay";
    String REFRESH_ORDER = "home/machine/refresh-orderno";//刷新订单
    String DELIVERY_WAY = "home/m/getEnabledShip";
    String getCaptcha = "home/machine/getCaptcha";
    String querySapId = "home/machine/getMachineInfo";
    String markBillStatus = "home/machine/updateOrderPrintStatus";
    String DEFAULT_GOODS = "home/machine/searchDefault";
    String SEACH_GOODS = "home/machine/search";
    String BRAND_GOODS = "home/machine/brand";
    String BRAND_DETIAL = "home/machine/brandDetail";
    String MAP_GUIDES = "h5/index.html#/map";

    String QUICK_ORDER_LIST = "newposService/presaleQuery";
    String QUICK_WXPAY = "newposService/wxPay";
    String ALI_PAYCODE = "newposService/aliPayTradePrecreate";
    String BILL_QUERY = "newposService/billQuery";
    String UPDATE_BILL = "newposService/updateBill";
    String APK_LOADDOWN = "http://47.92.91.12/mlhj.apk";
    String QUICK_ORDER_DATA = "newposService/getBillBySaleNo";
    String QUICK_BILL_INFO = "newposService/getShopParm";
    String QUICK_MARK_PRINT = "newposService/print";
}
