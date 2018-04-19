package com.maoye.mlh_slotmachine.util.httputil.cache;


import android.support.annotation.NonNull;

import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.NetWorkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class CacheInterceptor implements Interceptor {
   // public static final  int maxStale = 60 * 60 * 24;//保存一天
    public static final  double maxStale = 10;//保存一天
    public static final  double maxAge = 10 ;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean netAvailable = NetWorkUtils.checkNetworkAvailable(MyContext.getContext());
        Response response;
        if (!netAvailable) {
            request = request.newBuilder().cacheControl(new CacheControl.Builder().onlyIfCached()
                    .maxStale(10, TimeUnit.SECONDS).build())
                    .build();
        }
        if (netAvailable) {
            Response originalResponse = chain.proceed(request);
              /*    MediaType type = originalResponse.body().contentType();
            byte[] bs = originalResponse.body().bytes();*/
            response = originalResponse
                    .newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                   // .body(ResponseBody.create(type, bs))
                    .build();

            cacheUrl(request, response);
        } else {

            String key = getUrlKey(request);
            String cacheValue = CacheManager.getInstance().getCache(key);

            //构建一个新的response响应结果
            response = new Response.Builder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .body(ResponseBody.create(MediaType.parse("application/json"), cacheValue !=null ?cacheValue :""))
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .build();
        }
        return response;
    }


    @NonNull
    private String getUrlKey(Request request) {
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (request.method().equals("POST")) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(buffer.readString(charset));
            buffer.close();
        }

        return sb.toString();
    }


    private void cacheUrl(Request request, Response response) throws IOException {
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (request.method().equals("POST")) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(buffer.readString(charset));
            buffer.close();
        }
        String key = sb.toString();
        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String json = buffer.clone().readString(charset);
        CacheManager.getInstance().putCache(key, json);
    }
}
