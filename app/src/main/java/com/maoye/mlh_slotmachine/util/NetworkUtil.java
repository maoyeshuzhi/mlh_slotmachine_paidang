package com.maoye.mlh_slotmachine.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Rs on 2018/5/5.
 */

public class NetworkUtil {
    public  static boolean checkNetworkAvailable(Context context)  {
        try {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                try {
                    if (mNetworkInfo != null) {
                        return mNetworkInfo.isAvailable();
                    }
                } catch (Exception e) {
                    Log.e("Tag", "网络监测方法异常: ");
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
