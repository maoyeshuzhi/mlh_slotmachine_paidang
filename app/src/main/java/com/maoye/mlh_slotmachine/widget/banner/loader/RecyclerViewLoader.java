package com.maoye.mlh_slotmachine.widget.banner.loader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.maoye.mlh_slotmachine.widget.NotScrollRecyclerView;

/**
 * Created by Rs on 2018/5/13.
 */

public abstract class RecyclerViewLoader implements ImageLoaderInterface<RecyclerView> {
    @Override
    public RecyclerView createView(Context context) {
        NotScrollRecyclerView recyclerView = new NotScrollRecyclerView(context);
        return recyclerView;
    }
}