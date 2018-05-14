package com.maoye.mlh_slotmachine.widget.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;


public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayData(Context context, Object data, T view);
    T createView(Context context);
}
