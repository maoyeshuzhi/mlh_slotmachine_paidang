package com.maoye.mlh_slotmachine.view.mapguidesactivity;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.BrandGoodsListBean;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;
import com.maoye.mlh_slotmachine.webservice.URL;
import com.maoye.mlh_slotmachine.widget.BrandGoodsDialog;
import com.maoye.mlh_slotmachine.widget.WaiteDialog;
import com.maoye.mlh_slotmachine.widget.webview.OnWebViewPay;
import com.maoye.mlh_slotmachine.widget.webview.WebViewMapGuides;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图形导视
 */
public class MapguidesActivity extends MVPBaseActivity<MapguidesContract.View, MapguidesPresenter> implements MapguidesContract.View {


    @BindView(R.id.nodata_view)
    ImageView nodataView;
    @BindView(R.id.webView)
    WebViewMapGuides webView;
    @BindView(R.id.back)
    ImageButton back;
    private BrandGoodsDialog dialog;
    private String brandId;
    private BrandGoodsListBean bean;
    private WaiteDialog waiteDialog;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapguides);
        ButterKnife.bind(this);
        inidata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialog!=null&&dialog.isShowing()&&bean!=null) {
            dialog.setData(bean, brandId);
        }
    }

    /*    http://itao.maoye.cn/brand-details?shop_id=190&id=832*/
    private void inidata() {
        handler = new Handler();
        waiteDialog = new WaiteDialog(this);
        waiteDialog.show();
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(EnvConfig.instance().getBaseUkfUrl()+URL.MAP_GUIDES);
        webView.setOnWebViewPay(new OnWebViewPay() {
            @Override
            public void onWebPay(WebView view, String url, int payType) {
               // url = "http://itao.maoye.cn/brand-details?shop_id=190&id=832";
                if (url.contains("brand-details?shop_id")) {
                    String substring = url.substring(url.indexOf("?"), url.length());
                    brandId = substring.split("&")[1].split("=")[1];
                    mPresenter.brandDetial(brandId, 1, 10, 0);
                    Log.e("Tag", "onWebPay: " + url);
                }
            }
        });
    }

    /**
     * 刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
          waiteDialog.dismiss();
        }
    };

    @Override
    public void onSuccess(Object o) {
        bean = (BrandGoodsListBean) o;
        if (bean.getList().size() > 0) {
            dialog = new BrandGoodsDialog(MapguidesActivity.this);
            dialog.show();
            dialog.setData(bean, brandId);
        }else {
            Toast.getInstance().toast(this,"该品牌暂未开放线上商品，敬请期待",2);
        }
    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(true);
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            //   webView.clearHistory();
            //   webView.clearCache(true);
            //  webView.clearView();
            //webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    /**
     * 自定义WebChromeClient
     */
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                handler.postDelayed(runnable,2000);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
