package com.maoye.mlh_slotmachine.webservice;


import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.FeeBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.ProvinceBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.bean.VersionInfoBean;
import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiService {

    @GET(URL.HOME_DATA)
    Observable<BaseResult<HomeBean>> homedata();

    @GET(URL.GOODS_DETIALS)
    Observable<BaseResult<GoodsDetialsBean>> goodsDetials(@Query("id") int id);

    /**
     * 统计广告点击量
     *
     * @param id 广告id
     * @return
     */
    @GET(URL.STATISTIC)
    Observable<BaseResult<GoodsDetialsBean>> statistic(@Query("id") int id);

    @GET(URL.ACCOUNT_LOGIN)
    Observable<BaseResult> accountLogin(@Query("mobile") String mobile, @Query("password") String psw);

    /**
     * 手机号码登录
     *
     * @param mobile
     * @param type   2
     * @return
     */
    @GET(URL.MOBILE_LOGIN)
    Observable<BaseResult> mobileLogin(@Query("mobile") String mobile, @Query("type") int type);

    /**
     * @param id      商品id
     * @param spec_id 规格id
     * @param num     商品数量
     * @return
     */
    @GET(URL.ADD_CART)
    Observable<BaseResult> addCart(@Query("id") int id, @Query("spec_id") int spec_id, @Query("num") int num);

    /**
     * @return
     */
    @GET(URL.CART_LIST)
    Observable<BaseResult<List<GoodsBean>>> cartlist();

    @GET(URL.VERSION_INFO)
    Observable<BaseResult<VersionInfoBean>> versionInfo();

    /**
     * 省
     *
     * @return
     */
    @GET(URL.PROVINCE)
    Observable<BaseResult<List<ProvinceBean>>> province();

    /**
     * 市区
     *
     * @param id 市区id
     * @return
     */
    @GET(URL.PROVINCE)
    Observable<BaseResult<List<ProvinceBean>>> city(@Query("id") int id);

    /**
     * 添加收货地址
     *
     * @param name        姓名
     * @param phone       手机号码
     * @param province_id 省份id
     * @param city_id     市id
     * @param street      具体地址
     * @param area_id     区地址
     * @return
     */
    @GET(URL.ADD_ADDRESS)
    Observable<BaseResult> addAddress(@Query("name") String name, @Query("phone") String phone, @Query("province_id") int province_id, @Query("city_id") int city_id, @Query("area_id") int area_id, @Query("street") String street);

    /**
     * 地址列表
     *
     * @return
     */
    @GET(URL.ADDRESS_LIST)
    Observable<BaseResult<AddressBean>> addressList();

    /**
     * 修改购物车商品数量
     *
     * @param index 商品在购物车中的位置
     * @param num   最终数量
     * @return
     */
    @GET(URL.CHANGE_GOODS_NUM)
    Observable<BaseResult> changeGoodsNum(@Query("index") int index, @Query("num") int num);

    /**
     * 删除购物车
     *
     * @return
     */
    @GET(URL.DELETE_CART_ALL)
    Observable<BaseResult> deleteCartAll(@Query("type") int type);

    /**
     * 删除购物车商品
     *
     * @param indexs 商品位置字符串如   1,2,
     * @return
     */
    @GET(URL.DELETE_CART_ALL)
    Observable<BaseResult> deleteCart(@Query("indexs") String indexs);

    /**
     * @param area_id
     * @param ship_type    0-快递 1-EMS 2-平邮
     * @param product_info 商品id,商品数量集合
     * @return
     */
    @GET(URL.FREIGHT)
    Observable<BaseResult<FeeBean>> freight(@Query("area_id") int area_id, @Query("ship_type") int ship_type, @Query("product_info") String product_info);

    /**
     * 提交订单
     * @param submitOrderBean
     * @return
     */
    @POST(URL.SUBMIT_ORDER)
    Observable<BaseResult<OrderIdBean>> submitOrder(@Body SubmitOrderBean submitOrderBean);

}
