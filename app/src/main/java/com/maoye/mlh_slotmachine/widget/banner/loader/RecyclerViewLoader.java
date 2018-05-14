package com.maoye.mlh_slotmachine.widget.banner.loader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Rs on 2018/5/13.
 */

public abstract class RecyclerViewLoader implements ImageLoaderInterface<RecyclerView> {
    @Override
    public RecyclerView createView(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        return recyclerView;
    }
}