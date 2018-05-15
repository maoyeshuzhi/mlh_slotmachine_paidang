package com.maoye.mlh_slotmachine.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

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

/*    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent =this;
        while(!((parent = parent.getParent())   instanceof ViewPager)) ;// 循环查找viewPager
        parent.requestDisallowInterceptTouchEvent(true);

        return   super.dispatchTouchEvent(ev);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
