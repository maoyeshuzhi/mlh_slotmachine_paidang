package com.maoye.mlh_slotmachine.util;

import com.google.gson.GsonBuilder;
import com.maoye.mlh_slotmachine.bean.BaseResult;

/**
 * Created by Rs on 2018/5/13.
 */

public class FromJsonUtils {
    public static BaseResult fromJson(String json, Class clazz) {
        return new GsonBuilder()
                .registerTypeAdapter(BaseResult.class, new JsonFormatParser(clazz))
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create()
                .fromJson(json, BaseResult.class);
    }
}