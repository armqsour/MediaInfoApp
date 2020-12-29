package com.qs.mediainfoapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.jsbridge.BridgeHandler;
import com.qs.mediainfoapp.jsbridge.BridgeWebView;
import com.qs.mediainfoapp.jsbridge.CallBackFunction;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends BaseActivity {
    private BridgeWebView bridgeWebView;
    private String url;

    @Override
    public int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        bridgeWebView = findViewById(R.id.bridgeWebView);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
        }
        registJavaHandler();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = bridgeWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        bridgeWebView.loadUrl(url);
    }

    private void registJavaHandler() {
        bridgeWebView.registerHandler("goback", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
    }
}
