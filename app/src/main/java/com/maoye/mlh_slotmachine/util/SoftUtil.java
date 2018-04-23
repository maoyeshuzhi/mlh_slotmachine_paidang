package com.maoye.mlh_slotmachine.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Rs on 2018/4/23.
 */

public class SoftUtil {

    /**
     * @param type=0,没有延时； 1，延时300；2，延时800
     * @param et
     */

    public static void showSoft(int type, final EditText et) {
        Handler handeler = new Handler();
        switch (type) {
            case 0:
                InputMethodManager inputManager =
                        (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et, 0);
                break;
            case 1:
                handeler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(et, 0);
                    }
                }, 300);


                break;
            case 2:
                handeler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(et, 0);
                    }
                }, 900);
                break;
            default:
                break;
        }
    }


    public static void hideSoftInput(View view, Context context) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
// 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
