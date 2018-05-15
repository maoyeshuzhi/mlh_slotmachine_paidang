package com.maoye.mlh_slotmachine.widget.banner.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;


public class BannerPager extends ViewPager {
    private boolean scrollable = true;

    public BannerPager(Context context) {
        super(context);
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.scrollable) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.scrollable) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }
}
