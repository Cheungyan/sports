package me.zy.sports.activitys.eat;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import me.zy.sports.R;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.eat
 * Created by Administrator on 2019/5/7.
 * 描述：
 */
public class WebViewActivity extends AppCompatActivity {


    //网页
    private WebView mWebView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.mWebView);
        Intent intent=getIntent();
        String title = intent.getStringExtra("title");
       final String url = intent.getStringExtra("url");

        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebChromeClient());
        //加载网页
        mWebView.loadUrl(url);

        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //我接受这个事件
                return true;
            }
        });
    }
}
