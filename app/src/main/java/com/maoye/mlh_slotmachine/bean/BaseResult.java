package com.maoye.mlh_slotmachine.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liukun on 16/3/5.
 */
public class BaseResult<T> {

   private String msg;
   private boolean state;
   private int code;
    @SerializedName("data")
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
