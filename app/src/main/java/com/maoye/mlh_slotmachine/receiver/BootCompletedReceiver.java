package com.maoye.mlh_slotmachine.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;

/**
 * 监听开机广播
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals(ACTION)) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            boolean screenOn = pm.isScreenOn();
            if (!screenOn) {
                PowerManager.WakeLock wl = pm.newWakeLock(
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "BootCompletedReceiver");
                wl.acquire(8000); // 点亮屏幕
                wl.release(); // 释放
            }


            // 屏幕解锁
            KeyguardManager keyguardManager = (KeyguardManager) context
                    .getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("BootCompletedReceiver");
            // 屏幕锁定
            keyguardLock.reenableKeyguard();
            keyguardLock.disableKeyguard(); // 解锁
            Intent mainActivityIntent = new Intent(context, HomeActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    }

}
