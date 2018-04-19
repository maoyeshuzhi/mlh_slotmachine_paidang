package com.maoye.mlh_slotmachine.util.httputil.subscribers;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.util.httputil.ApiException;

/**
 * Created by liukun on 16/3/10.
 */
public interface  SubscriberOnNextListener<T> {
    void onNext(T t) ;
    void onFail(Throwable apiException);
}
