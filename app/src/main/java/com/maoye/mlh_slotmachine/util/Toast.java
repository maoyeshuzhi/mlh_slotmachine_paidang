package com.maoye.mlh_slotmachine.util;

import android.view.Gravity;

/**
 * Created by Rs on 2018/4/9.
 */

public class Toast {
    private static String oldMsg ;
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int BOTTOM = 3;
    protected static android.widget.Toast toast = null;

    private static Toast instance;

    public static synchronized Toast getInstance() {
        if (instance == null) {
            instance = new Toast();
        }
        return instance;
    }

    /**
     * 单例吐司
     * @param cont
     * @param msg
     * @param type 1:顶部 2：居中：3：底部
     */
    public void toast(android.content.Context cont, String msg, int type) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(cont, msg, android.widget.Toast.LENGTH_SHORT);
            toastPosition(toast,type);
        } else {
            if(!msg.equals(oldMsg)){
                oldMsg = msg;
                toast.setText(msg);
            }
        }
        toast.show();
    }


    private void toastPosition(android.widget.Toast toast, int type){
        if (type == TOP) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
        } else if (type == CENTER) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        } else {
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        }
    }
}
