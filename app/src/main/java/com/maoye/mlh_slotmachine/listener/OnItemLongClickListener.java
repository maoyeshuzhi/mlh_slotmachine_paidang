package com.maoye.mlh_slotmachine.listener;

import android.view.View;

/**
 * Created by Rs on 2018/4/11.
 */

public interface OnItemLongClickListener<T> {
    void onItemLongClick(View view, int position,T data);
}
