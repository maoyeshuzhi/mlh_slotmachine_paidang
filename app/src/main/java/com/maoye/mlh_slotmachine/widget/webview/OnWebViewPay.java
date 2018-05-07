package com.maoye.mlh_slotmachine.widget.webview;

import android.webkit.WebView;

/**
 * Created by Rs on 2018/5/4.
 */

public interface OnWebViewPay {
    void onWebPay(WebView view, String url, int payType);
}