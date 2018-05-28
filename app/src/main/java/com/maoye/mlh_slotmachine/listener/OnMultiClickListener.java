package com.maoye.mlh_slotmachine.listener;

import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rs on 2018/5/24.
 */

public abstract class OnMultiClickListener implements View.OnClickListener {
    private int MIN_TIME;
    private static long lasteCLickTime;


    public abstract void onMultiClick(View view);

    /**
     *
     * @param time 时间间隔 秒
     */
    protected OnMultiClickListener(int time) {
        this.MIN_TIME = time;
    }

    @Override
    public void onClick(View view) {
        long curClickTIme  = System.currentTimeMillis();
        Date parse = new Date(lasteCLickTime);
        Date curDate = new Date(curClickTIme);
        if ( curDate.getSeconds()-parse.getSeconds()> MIN_TIME) {
            lasteCLickTime = curClickTIme;
            onMultiClick(view);
        }
    }
}
