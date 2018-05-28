package com.maoye.mlh_slotmachine.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.maoye.mlh_slotmachine.bean.BaseResult;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rs on 2018/5/13.
 */

public class JsonFormatParser implements JsonDeserializer<BaseResult> {
    private Class mClass;
    private String key;

    public JsonFormatParser(Class clazz,String key) {
        this.mClass = clazz;
        this.key = key;
    }

    @Override
    public BaseResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // 根据Json元素获取Json对象。
        JsonObject mJsonObject = json.getAsJsonObject();
        BaseResult mResult = new BaseResult();
        // 由于Json是以键值对的形式存在的，此处根据键(data)获取对应的Json字符串。
        String mJson = mJsonObject.get(key).toString();
        // 判断是Array还是Object类型。
        if (mJsonObject.get(key).isJsonArray() && !mJsonObject.get(key).isJsonNull()) {
            mResult.setData(fromJsonArray(mJson, mClass));
            mResult.setDataType(1);
        } else if (mJsonObject.get(key).isJsonObject() && !mJsonObject.get(key).isJsonNull()) {
            mResult.setData(fromJsonObject(mJson, mClass));
            mResult.setDataType(0);
        } else if (mJsonObject.get(key).isJsonPrimitive() && !mJsonObject.get(key).isJsonNull()) {
            // 服务端返回data的值为"{}","[]"，将对象或者集合以字符串的形式返回回来，先去除两边的双引号，再去掉转义字符。
            String mNewJson = mJson.substring(1, mJson.length() - 1).replaceAll("\\\\", "");
            // 根据处理好的Json字符串判断是集合还是对象，再进行解析。
            if (mNewJson.startsWith("[") || mNewJson.endsWith("]")) {
                mResult.setData(fromJsonArray(mNewJson, mClass));
                mResult.setDataType(1);
            } else if (mNewJson.startsWith("{") || mNewJson.endsWith("}")) {
                mResult.setData(fromJsonObject(mNewJson, mClass));
                mResult.setDataType(0);
            } else {
                mResult.setData(fromJsonObject(mResult.toString(), mClass));
                mResult.setDataType(2);
            }
        } else if (mJsonObject.get(key).isJsonNull() || mJsonObject.get(key).getAsString().isEmpty()) {
            mResult.setData(fromJsonObject(mResult.toString(), mClass));
            mResult.setDataType(2);
        }
        // 根据键获取返回的状态码。
        mResult.setCode(mJsonObject.get("code").getAsInt());
        // 根据键获取返回的状态信息。
        mResult.setMsg(mJsonObject.get("msg").getAsString());
        return mResult;
    }


    private   <T> ArrayList<T> fromJsonArray(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JSONObject>>() {
        }.getType();
        ArrayList<JSONObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjects) {
            if (jsonObject != null)
                arrayList.add(new Gson().fromJson(jsonObject.toString(), clazz));
        }
        return arrayList;
    }

    /**
     * 用来解析对象
     */
    private <T> T fromJsonObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
