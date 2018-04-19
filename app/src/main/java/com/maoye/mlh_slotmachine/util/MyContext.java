package com.maoye.mlh_slotmachine.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Rs on 2018/4/9.
 */

public class MyContext {
    public static android.content.Context appContext;
    public static void setContext(android.content.Context context) {
        if (null == appContext)
            appContext = context;
    }

    public static Context getContext(){
        return appContext;
    }
}
