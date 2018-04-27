package com.maoye.mlh_slotmachine;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.MyContext;

/**
 * Created by Rs on 2018/4/9.
 */

public class MlhApplication extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        MyContext.setContext(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyContext.setContext(getApplicationContext());
    }

}
