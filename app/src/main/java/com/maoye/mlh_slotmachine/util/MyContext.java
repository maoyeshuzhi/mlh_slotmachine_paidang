package com.maoye.mlh_slotmachine.util;

import android.content.Context;


/**
 * Created by Rs on 2018/4/9.
 */

public class MyContext {
    public static Context appContext;
    public static void setContext(android.content.Context context) {
        if (null == appContext)
            appContext = context;
    }

    public static Context getContext(){
        return appContext;
    }
}
