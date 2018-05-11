package com.maoye.mlh_slotmachine.mvp;

import android.text.TextUtils;

import com.maoye.mlh_slotmachine.webservice.ApiService;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rs on 2018/4/11.
 */

public class BaseModel extends BaseRetrofit{

    private static final String TAG = "BaseModel";

    protected ApiService mServletApi;   //所有的注解接口

    protected Map<String, Object> mParams = new HashMap<>();

    public BaseModel() {
        super();
        mServletApi = retrofit.create(ApiService.class);

    }



    protected void addParams(String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mParams.put(key, value);
    }

    protected void addParams(Map<String, Object> params) {
        if (null == params) {
            return;
        }
        mParams.putAll(params);
    }
}
