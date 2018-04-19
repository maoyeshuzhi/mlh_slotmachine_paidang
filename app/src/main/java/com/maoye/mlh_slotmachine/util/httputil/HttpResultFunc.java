package com.maoye.mlh_slotmachine.util.httputil;

import android.util.Log;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.bean.BaseResult;

import io.reactivex.functions.Function;
/**
 * Created by Rs on 2017/12/15.
 */

public class HttpResultFunc<T> implements Function<BaseResult<T>, T> {


    @Override
    public T apply(BaseResult<T> tBaseResult) throws Exception {
        if (tBaseResult.getCode()==1000007) {
            throw new ApiException(1000007);
        }
        Log.e("TAG", "call: "+ new Gson().toJson(tBaseResult.getData()));
        return (T) tBaseResult;
    }
}