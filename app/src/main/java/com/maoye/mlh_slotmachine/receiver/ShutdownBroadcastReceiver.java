package com.maoye.mlh_slotmachine.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rs on 2018/5/2.
 * 监听关机广播
 */

public class ShutdownBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ShutdownBroadcastReceiver";

    private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            questStatus(0);
        }
    }

    private void questStatus(int iStatus) {
        Observable<BaseResult> baseResultObservable = BaseRetrofit.getInstance().apiService.deveice_status(iStatus);
        baseResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    BaseObserver<BaseResult> observer = new BaseObserver<BaseResult>(MyContext.appContext) {
        @Override
        protected void onBaseNext(BaseResult data) {

        }

        @Override
        protected void onBaseError(Throwable t) {

        }
    };
}
