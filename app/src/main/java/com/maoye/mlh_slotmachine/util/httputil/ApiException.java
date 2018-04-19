package com.maoye.mlh_slotmachine.util.httputil;

import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.Toast;

/**
 * Created by liukun on 16/3/10.
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 1000007;
    public static final int WRONG_PASSWORD = 101;
    public static final int ERROR_404 = -1;
    /*错误码*/
    private int mCode;
    private String mMessage;

    public int getmCode() {
        return mCode;
    }

    public String getMsg() {
        return mMessage;
    }


    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode, null));
    }


    public ApiException(int resultCode, String detailMessage) {
        this(getApiExceptionMessage(resultCode, detailMessage));
        mCode = resultCode;
        mMessage = detailMessage;
    }


    public ApiException(String detailMessage) {
        super(detailMessage);
        mMessage = detailMessage;
    }


    private static String getApiExceptionMessage(int code, String detailMessage) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "请登录";

                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                if (detailMessage != null) {
                    message = detailMessage;
                } else {
                    message = "未知错误";
                }
        }
        Toast.getInstance().toast(MyContext.appContext, message, 2);
        new Throwable(code + "");
        return message;
    }
}

