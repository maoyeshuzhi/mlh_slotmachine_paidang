package com.maoye.mlh_slotmachine.util.httputil;

import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.Toast;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rs on 2018/4/25.
 */

public class AddParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url().newBuilder()
               .addQueryParameter("device_no", DeviceInfoUtil.getDeviceId())
               // .addQueryParameter("device_no", "cc:b8:a8:50:3a:98")
                .build();
        Request build = request.newBuilder().url(httpUrl).build();
        LogUtils.e("-请求Url---"+httpUrl.toString());
        return chain.proceed(build);
    }
}
