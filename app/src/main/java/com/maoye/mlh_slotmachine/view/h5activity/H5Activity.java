package com.maoye.mlh_slotmachine.view.h5activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.widget.webview.OnWebViewPay;
import com.maoye.mlh_slotmachine.widget.webview.ProgressWebView;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class H5Activity extends MVPBaseActivity<H5Contract.View, H5Presenter> implements H5Contract.View {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.webView)
    ProgressWebView webView;
    private String inset_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmail);
        ButterKnife.bind(this);
        inidata();
    }

    private void inidata() {
        Intent intent = getIntent();
        inset_url = intent.getStringExtra(Constant.KEY)+"&from=machine";
        webView.loadUrl(inset_url);
        webView.setFocusable(true);//设置有焦点
        webView.setFocusableInTouchMode(true);//设置可触摸
        webView.setOnWebViewPay(new OnWebViewPay() {
            @Override
            public void onWebPay(WebView view, String url, int payType) {
                switchPayType(url, payType);

            }
        });
    }

    private void switchPayType(String url, int payType) {
        switch (payType) {
            case Constant.ALPAY:
                Intent intent = null;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setComponent(null);
                    startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.WXPAY:
                Intent wxIntent = new Intent();
                wxIntent.setAction(Intent.ACTION_VIEW);
                wxIntent.setData(Uri.parse(url));
                startActivity(wxIntent);
                break;
            case Constant.TITLE:
                title.setText(url+"");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearCache(true);
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && !webView.canGoBack()) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.back, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                try {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                } catch (Exception e) {
                    webView.goBack();
                    e.printStackTrace();
                }
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
