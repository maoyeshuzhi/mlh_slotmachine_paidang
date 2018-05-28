package com.maoye.mlh_slotmachine.widget.webview;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.util.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rs on 2017/12/6.
 */

public class WebViewMapGuides extends WebView {
    private WebViewProgressBar progressBar;//进度条的矩形（进度线）
    private Handler handler;
    private WebView mWebView;
    private OnWebViewPay onWebViewPay;
    private String aliUrl;
    private String wxUrl, oldMine;
    private String wxXieyiUrl;
    private RelativeLayout hintView;
    private TextView title;
    private String titleContet;

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
        //实例化进度条
        progressBar = new WebViewProgressBar(context);
        //设置进度条的size
        progressBar.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //刚开始时候进度条不可见
     //   progressBar.setVisibility(GONE);
        //把进度条添加到webView里面
        // addView(progressBar);
        //初始化handle
        handler = new Handler();
        mWebView = this;
        initSettings();
    }

    private void initSettings() {
        // 初始化设置
        WebSettings mSettings = this.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setDomStorageEnabled(true);//开启DOM
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setBuiltInZoomControls(true);// 支持缩放
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        // mSettings.setBlockNetworkImage(true);
        mSettings.setBlockNetworkImage(false);
        mSettings.setAppCacheEnabled(true);//开启缓存机制
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new MyWebChromeClient());
    }

    public void setHintView(RelativeLayout hintView, TextView title, String titleContent) {
        this.hintView = hintView;
        this.title = title;
        this.titleContet = titleContent;
    }


    /**
     * 自定义WebChromeClient
     */
    private class MyWebChromeClient extends WebChromeClient {
        /**
         * 进度改变的回掉
         *
         * @param view        WebView
         * @param newProgress 新进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);
                handler.postDelayed(runnable, 200);//0.2秒后隐藏进度条
            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(VISIBLE);
            }
            //设置初始进度10，这样会显得效果真一点，总不能从1开始吧
            if (newProgress < 10) {
                newProgress = 10;
            }
            //不断更新进度
            progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebViewPay.onWebPay(view, view.getTitle(), Constant.TITLE);

        }
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
            return super.shouldOverrideUrlLoading(view, url);
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
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            goBack();
            if (description.contains("net::ERR_INTERNET_DISCONNECTED")) {
                if (hintView != null) {
                    hintView.setVisibility(VISIBLE);
                    title.setText(titleContet);
                    view.setVisibility(GONE);
                }
            }
            // super.onReceivedError(view, errorCode, description, failingUrl);

        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            WebViewMapGuides.this.requestFocus();
            WebViewMapGuides.this.requestFocusFromTouch();
        }
    }

    private boolean wxPayWay2(WebView view, String url) {
        if (wxXieyiUrl != null && wxXieyiUrl.equals(url)) {
            return false;
        } else {
            wxXieyiUrl = url;
            onWebViewPay.onWebPay(view, url, Constant.WXPAY);
            return true;
        }
    }

    private boolean wxPay_do_weight(WebView view, String url) {
        if (wxUrl != null && wxUrl.equals(url)) {
            return false;
        } else {
            wxUrl = url;
            onWebViewPay.onWebPay(view, url, Constant.WXPAY);
            return true;
        }
    }

    private boolean wxPayWay1(String url) {
        if (wxUrl != null && wxUrl.equals(url)) {
            return false;
        } else {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Referer", "http://serve.maoye.cn");
            mWebView.loadUrl(url, extraHeaders);
            return true;
        }
    }

    /**
     * 登录去重，防止死循环
     *
     * @param url
     * @return
     */
    private boolean login_de_weight(String url) {
        if (oldMine != null && oldMine.equals(url)) {
            goBack();
            oldMine = null;
            return false;
        } else {
            oldMine = url;
            loadUrl(url);
            return true;
        }
    }

    /**
     * 刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };
}