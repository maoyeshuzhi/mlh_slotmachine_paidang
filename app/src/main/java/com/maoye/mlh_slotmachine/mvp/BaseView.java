package com.maoye.mlh_slotmachine.mvp;

import android.content.Context;
public interface BaseView<T> {
     Context getContext();
     void onSuccess(T t);
}
