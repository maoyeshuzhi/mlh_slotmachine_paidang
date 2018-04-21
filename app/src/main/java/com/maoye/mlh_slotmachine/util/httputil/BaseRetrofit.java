package com.maoye.mlh_slotmachine.util.httputil;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.CustomGsonConverterFactory;
import com.maoye.mlh_slotmachine.webservice.ApiService;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;


/**
 * Created by liukun on 16/3/9.
 */
public class BaseRetrofit {

    private static final int DEFAULT_TIMEOUT = 5;
    private static final int SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
    private static final int DEFAULT_TIME = 10;    //默认超时时间
    private final long RETRY_TIMES = 1;   //重订阅次数
    protected Retrofit retrofit;
    private ApiService apiService;

    //构造方法私有
    protected BaseRetrofit() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(EnvConfig.instance().getWebServiceBaseUrl())
                .client(getHttpClient())
       /*      .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                */
                .addConverterFactory(CustomGsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    protected <T> void toSubscribe(io.reactivex.Observable<T> observable, io.reactivex.Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
              //  .timeout(DEFAULT_TIME, TimeUnit.SECONDS)    //重连间隔时间
                .retry(RETRY_TIMES)
//          .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
//                @Override
//                public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
//                    return null;
//                }
//           })
                .subscribe(observer);   //订阅
    }


    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        generateTokenHttpClient(httpClient);
        return httpClient.build();
    }

    private void generateTokenHttpClient(OkHttpClient.Builder httpClient) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("jsonUrl", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存路径
        String cacheFile = MyContext.appContext.getCacheDir() + "/http";
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);

        httpClient
               // .cache(cache)
                .writeTimeout(20000L, TimeUnit.MILLISECONDS)
                .connectTimeout(40000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
               // .addInterceptor(new com.maoye.mlh_slotmachine.util.httputil.cache.CacheInterceptor())
                .addInterceptor(loggingInterceptor).build();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final BaseRetrofit INSTANCE = new BaseRetrofit();
    }

    //获取单例
    public static BaseRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
