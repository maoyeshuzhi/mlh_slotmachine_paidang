package com.maoye.mlh_slotmachine.util.httputil.progress;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.widget.WaiteDialog;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private WaiteDialog pd;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null &&context!=null) {
            pd = new WaiteDialog(context);
            pd.setCancelable(cancelable);
        }
            if (cancelable &&pd!=null) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        try {
                            mProgressCancelListener.onCancelProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (pd!=null &&!pd.isShowing()) {
                try {
                    pd.show();
                } catch (Exception e) {
                    LogUtils.e("Unable to add window -- token null is not for an application");
                }
            }
    }

    private void dismissProgressDialog() {
        if (pd!=null &&pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}