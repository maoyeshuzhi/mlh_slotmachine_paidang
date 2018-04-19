package com.maoye.mlh_slotmachine.listener;

import android.view.View;

/**
 * Created by Rs on 2018/4/11.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view,int position, T data);
}