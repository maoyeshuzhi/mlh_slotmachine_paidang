package com.maoye.mlh_slotmachine.listener;

import android.view.View;

/**
 * Created by Rs on 2018/4/11.
 */

public interface OnItemChildClickListener<T> {
    /**
     *
     * @param view
     * @param type
     * @param position
     * @param data
     */
    void onChildItemClick(View view,int type, int position, T data);
}
