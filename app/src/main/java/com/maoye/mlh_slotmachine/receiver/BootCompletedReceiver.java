package com.maoye.mlh_slotmachine.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.maoye.mlh_slotmachine.bean.BaseInfo;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.SapIdBean;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
            startup(1);
            baseInfo();
            if (!screenOn) {
                PowerManager.WakeLock wl = pm.newWakeLock(
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "BootCompletedReceiver");
                wl.acquire(10000); // 点亮屏幕
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

    /**
     * 开机关机
     *
     * @param status 1：开机 0：关机
     */
    public void startup(int status) {
        Observable<BaseResult> baseResultObservable = BaseRetrofit.getInstance().mServletApi.deveice_status(status);
        baseResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(context, false) {
                    @Override
                    protected void onBaseNext(BaseResult data) {
                        LogUtils.e("开机成功");
                    }

                    @Override
                    protected void onBaseError(Throwable t) {

                    }
                });
    }


    public void baseInfo() {
        Observable<BaseResult<SapIdBean>> baseResultObservable = BaseRetrofit.getInstance().mServletApi.querySapId();
        baseResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(20)
                .subscribe(new BaseObserver<BaseResult<SapIdBean>>(context, false) {
                    @Override
                    protected void onBaseNext(BaseResult<SapIdBean> data) {
                        Log.e("Tag", "getSap_id: "+data.getData().getSap_id() );
                        BaseInfo.setSapId(data.getData().getSap_id());
                        BaseInfo.setStoreName(data.getData().getShop_name());
                    }

                    @Override
                    protected void onBaseError(Throwable t) {

                    }
                });
    }


}
