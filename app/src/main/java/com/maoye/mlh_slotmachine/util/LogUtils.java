package com.maoye.mlh_slotmachine.util;

import android.util.Log;

/**
 * Created by Rs on 2018/4/16.
 */

public class LogUtils {
    public static void e(String msg) {
        Log.e("---------TAG---------", msg);
    }

    public static void e(String tag,String msg) {
        Log.e("---------"+tag+"---------", msg);
    }
}
