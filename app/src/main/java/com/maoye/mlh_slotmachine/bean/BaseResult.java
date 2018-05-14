package com.maoye.mlh_slotmachine.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liukun on 16/3/5.
 */
public class BaseResult<T> {

   private String msg;
   private boolean state;
   private int status;//0表示成功快付）
    private String errorMsg;//（快付）
    /**
     * 扩展字段
     * 0:data为对象
     * 1:data为集合
     * 2:date为空或者null字段
     */
    private int dataType;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
