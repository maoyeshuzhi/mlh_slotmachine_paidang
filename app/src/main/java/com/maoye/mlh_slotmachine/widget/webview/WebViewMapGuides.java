package com.maoye.mlh_slotmachine.widget.webview;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.widget.WaiteDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rs on 2017/12/6.
 */

public class WebViewMapGuides extends WebView {
    private WebView mWebView;
    private OnWebViewPay onWebViewPay;

    public interface DisplayFinish {
        void After();
    }

    public DisplayFinish df;

    public void setDf(DisplayFinish df) {
        this.df = df;
    }

    //onDraw表示显示完毕
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (df != null) df.After();
    }

    public void setOnWebViewPay(OnWebViewPay onWebViewPay) {
        this.onWebViewPay = onWebViewPay;
    }

    public WebViewMapGuides(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWebView = this;
        initSettings();
    }

    private void initSettings() {

        // 初始化设置
        WebSettings mSettings = this.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setDomStorageEnabled(true);//开启DOM
      //  mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面

        //  mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setBuiltInZoomControls(true);// 支持缩放
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        // mSettings.setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        mSettings.setBlockNetworkImage(false);
        mSettings.setAppCacheEnabled(true);//开启缓存机制
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        setWebViewClient(new MyWebClient());

    }
    private class MyWebClient extends WebViewClient {
        private String startUrl;

        /**
         * 页面开始加载调用的方法
         *
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            startUrl = url;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(onWebViewPay!=null&&!url.contains("h5/index.html#/map")) {
                onWebViewPay.onWebPay(view, url, Constant.TITLE);
                return true;
            }else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        /**
         * 页面加载过程中，加载资源回调的方法
         *
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
          //  super.onReceivedSslError(view, handler, error);
            handler.proceed();// 接受所有网站的证书
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
          //  goBack();
            super.onReceivedError(view, errorCode, description, failingUrl);

        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            WebViewMapGuides.this.requestFocus();
            WebViewMapGuides.this.requestFocusFromTouch();
        }
    }
}