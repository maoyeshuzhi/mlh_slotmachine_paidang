package com.maoye.mlh_slotmachine.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.maoye.mlh_slotmachine.R;

/**
 * Created by Rs on 2018/4/26.
 */

public class Poputils {
    /**
     * 底部弹窗Pop
     *
     * @param view
     * @param parent
     * @param activity
     * @return
     */
    public static PopupWindow getPop(View view, int parent, final Activity activity) {
        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        View rootview = LayoutInflater.from(activity).inflate(parent, null);
        pop.setBackgroundDrawable(new BitmapDrawable());
  
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f, activity);
            }
        });
        pop.setAnimationStyle(R.style.table_pop);
        pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        return pop;
    }

    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
