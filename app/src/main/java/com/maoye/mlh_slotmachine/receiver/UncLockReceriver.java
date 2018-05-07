package com.maoye.mlh_slotmachine.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rs on 2018/5/3.
 * 解锁广播
 */

public class UncLockReceriver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.USER_PRESENT";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION)){
         /*   Intent mainActivityIntent = new Intent(context, HomeActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);*/

        }

    }


}
