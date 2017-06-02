package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;


/**
 * Created by sll on 2017/6/1.
 */

public class DealRulesActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.wv_html_info)
    WebView wvHtmlInfo;
    private WebViewClient mWebViewClient = new WebViewClient();

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_web;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        int tag = bundle.getInt("tag");
        final String url = bundle.getString("bundle");
        if (tag == 1) {
            ntTitle.setTitleText("买卖规则");
        } else if (tag == 2) {
            ntTitle.setTitleText("关于我们");
        }
        if (url != null) {
            wvHtmlInfo.loadUrl(url);
            LogUtils.loge("得到的url:"+url);
        }
        wvHtmlInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = wvHtmlInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }


}
