package com.maoye.mlh_slotmachine.util.httputil;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

   @GET(URL.MOBILE_LOGIN)
    Observable<BaseResult> mobileLogin(@Query("mobile") String mobile,@Query("type")int type);

}
