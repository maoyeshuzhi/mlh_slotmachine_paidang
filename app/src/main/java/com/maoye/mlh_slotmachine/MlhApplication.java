package com.maoye.mlh_slotmachine;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;


import com.maoye.mlh_slotmachine.bean.MyObjectBox;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.tencent.bugly.crashreport.CrashReport;

import io.objectbox.BoxStore;

/**
 * Created by Rs on 2018/4/9.
 */

public class MlhApplication extends Application{
    public static BoxStore mBoxStore;
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
        CrashReport.initCrashReport(getApplicationContext(), "ec8724a1fa", false);
        mBoxStore = MyObjectBox.builder().androidContext(this).build();

    }

    public BoxStore getBoxStore(){
        return mBoxStore;
    }

}
