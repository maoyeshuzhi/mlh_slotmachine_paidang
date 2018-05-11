package com.maoye.mlh_slotmachine.util.httputil;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.maoye.mlh_slotmachine.util.httputil.cookies.ClearableCookieJar;
import com.maoye.mlh_slotmachine.util.httputil.cookies.PersistentCookieJar;
import com.maoye.mlh_slotmachine.util.httputil.cookies.cache.SetCookieCache;
import com.maoye.mlh_slotmachine.util.httputil.cookies.persistence.SharedPrefsCookiePersistor;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.CustomGsonConverterFactory;
import com.maoye.mlh_slotmachine.webservice.ApiService;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;

import java.io.File;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by liukun on 16/3/9.
 */
public class BaseRetrofit {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final int SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
    private static final int DEFAULT_TIME = 10;    //默认超时时间
    private final long RETRY_TIMES = 1;   //重订阅次数
    protected Retrofit retrofit,quickPayRetrofit;
    public ApiService apiService ,quickApiService;
    private ClearableCookieJar cookieJar;

    //构造方法私有
    protected BaseRetrofit() {
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyContext.appContext));
       // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(EnvConfig.instance().getWebServiceBaseUrl())
                .client(getHttpClient())
                .addConverterFactory(CustomGsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        quickPayRetrofit = new Retrofit.Builder()
                .baseUrl(EnvConfig.instance().getQuickPayWebServiceBaseUrl())
                .client(getHttpClient())
                .addConverterFactory(CustomGsonConverterFactoryQuickPay.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        quickApiService = quickPayRetrofit.create(ApiService.class);
        apiService = retrofit.create(ApiService.class);
    }

    public  <T> void toSubscribe(io.reactivex.Observable<T> observable, io.reactivex.Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
                .subscribe(observer);   //订阅
    }

    /**
     *
     * @param time 重复请求次数
     * @param observable
     * @param observer
     * @param <T>
     */
    protected <T> void toSubscribe(int time, io.reactivex.Observable<T> observable, io.reactivex.Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
                .retry(time)
                .repeatWhen(new Function<io.reactivex.Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull io.reactivex.Observable<Object> objectObservable) throws Exception {
                        return null;
                    }
                })
                .subscribe(observer);   //订阅
    }


    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        generateTokenHttpClient(httpClient);
        return httpClient.build();
    }

    private void generateTokenHttpClient(OkHttpClient.Builder httpClient) {
        //缓存路径
        String cacheFile = MyContext.appContext.getCacheDir() + "/http";
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);

        httpClient
                .cookieJar(cookieJar)
                // .cache(cache)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new AddParamInterceptor())
                // .addInterceptor(new com.maoye.mlh_slotmachine.util.httputil.cache.CacheInterceptor())
                .build();

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
