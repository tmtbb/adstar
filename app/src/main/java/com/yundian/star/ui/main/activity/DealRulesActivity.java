package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;

import static com.yundian.star.R.id.webView;


/**
 * Created by sll on 2017/6/1.
 */

public class DealRulesActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.wv_html_info)
    WebView webView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private WebViewClient mWebViewClient = new WebViewClient();
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_web;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        initData();
        setWebViewSettings();
        setWebView();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        int tag = bundle.getInt("tag");
        url = bundle.getString("bundle");
        if (tag == 1) {
            ntTitle.setTitleText("买卖规则");
        } else if (tag == 2) {
            ntTitle.setTitleText("关于我们");
        }

    }

    private void setWebViewSettings() {
        WebSettings webSettings = webView.getSettings();
        // 打开页面时， 自适应屏幕
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 便页面支持缩放
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setSupportZoom(true); //支持缩放
//        webSettings.setBuiltInZoomControls(true); // 放大和缩小的按钮，容易引发异常 http://blog.csdn.net/dreamer0924/article/details/34082687

        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void setWebView() {
        if (url == null) {
            return;
        }
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}
