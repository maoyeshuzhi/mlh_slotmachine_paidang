package com.maoye.mlh_slotmachine.webservice;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.VersionInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET(URL.HOME_DATA)
    Observable<BaseResult<HomeBean>> homedata(@Query("device_no") String device_no);

    @GET(URL.GOODS_DETIALS)
    Observable<BaseResult<GoodsDetialsBean>> goodsDetials(@Query("id") int id);

    /**
     * 统计广告点击量
     * @param id 广告id
     * @return
     */
    @GET(URL.STATISTIC)
    Observable<BaseResult<GoodsDetialsBean>> statistic(@Query("id") int id);

    @GET(URL.ACCOUNT_LOGIN)
    Observable<BaseResult> accountLogin(@Query("mobile") String mobile,@Query("password")String psw);

    /**
     * 手机号码登录
     * @param mobile
     * @param type 2
     * @return
     */
   @GET(URL.MOBILE_LOGIN)
    Observable<BaseResult> mobileLogin(@Query("mobile") String mobile,@Query("type")int type);

    /**
     *
     * @param device_no 设备号
     * @param id  商品id
     * @param spec_id 规格id
     * @param num 商品数量
     * @return
     */
   @GET(URL.ADD_CART)
    Observable<BaseResult> addCart(@Query("device_no") String device_no,@Query("id")int id,@Query("spec_id")int spec_id,@Query("num")int num);

    /**
     *
     * @param mobile
     * @return
     */
   @GET(URL.CART_LIST)
    Observable<BaseResult> cartlist(@Query("device_no") String mobile);

   @GET(URL.VERSION_INFO)
    Observable<BaseResult<VersionInfoBean>> versionInfo();

}
