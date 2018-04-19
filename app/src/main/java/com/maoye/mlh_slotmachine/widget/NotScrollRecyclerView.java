package com.maoye.mlh_slotmachine.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Rs on 2018/4/11.
 */

public class NotScrollRecyclerView extends RecyclerView {
    public NotScrollRecyclerView(Context context) {
        super(context);
    }

    public NotScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
