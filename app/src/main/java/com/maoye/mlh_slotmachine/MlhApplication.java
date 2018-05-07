package com.maoye.mlh_slotmachine;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.maoye.mlh_slotmachine.bean.MyObjectBox;
import com.maoye.mlh_slotmachine.util.MyContext;

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
        mBoxStore = MyObjectBox.builder().androidContext(this).build();

    }

    public BoxStore getBoxStore(){
        return mBoxStore;
    }

}
