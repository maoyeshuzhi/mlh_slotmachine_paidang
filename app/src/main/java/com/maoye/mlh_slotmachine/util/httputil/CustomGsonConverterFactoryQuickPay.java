package com.maoye.mlh_slotmachine.util.httputil;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.CustomGsonRequestBodyConverter;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.CustomGsonResponseBodyConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Rs on 2017/12/15.
 */

public class CustomGsonConverterFactoryQuickPay extends Converter.Factory {

    private final Gson gson;

    private CustomGsonConverterFactoryQuickPay(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static CustomGsonConverterFactoryQuickPay create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactoryQuickPay create(Gson gson) {
        return new CustomGsonConverterFactoryQuickPay(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverterQuickPay<>(gson, adapter);
}


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);
    }
}