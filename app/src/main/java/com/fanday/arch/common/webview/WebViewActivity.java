package com.fanday.arch.common.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.fanday.arch.R;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;

import butterknife.ButterKnife;

/**
 * @version V2.0
 * @Title: StaticHelpContentActivity
 * @Description: 静态页
 * @author: niuzhihua
 * @date: 15/09/14
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    // 添加对网页内的视频全屏播放
    WebView webView;

    String url_name;//传的字符标识
    String url_content;//传的字符内容
    int cookieFlag; //是否设置cookie  0=否；；1=是；

    public static WeakReference<WebViewActivity> tempInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        ButterKnife.bind(this);
        initView();
    }


    void initView() {
        url_name = this.getIntent().getStringExtra("url_name");
        url_content = this.getIntent().getStringExtra("url_content");
        cookieFlag = this.getIntent().getIntExtra("cookieFlag", 0);

        // 初始化 webview （支持全屏播放）
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new HXWebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.loadUrl(url_content);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String s2, String s3, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }

    private class HXWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            // 在默认情况下，通过loadUrl(String url)方法，可以顺利load。
            // 但是，当load有ssl层的https页面时，如果这个网站的安全证书在Android无法得到认证，WebView就会变成一个空白页，
            // 而并不会像PC浏览器中那样跳出一个风险提示框。因此，我们必须针对这种情况进行处理。(这个证书限于2.1版本以上的Android 系统才可以)
            // 默认的处理方式，WebView变成空白页
            // handler.cancel();
            // 接受证书
            sslErrorHandler.proceed();

        }

        //视频全屏播放处理
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    //webview 返回键处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public static void newInstance(Context context, String url_name, String url_content, int cookieFlag) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url_name", url_name);
        intent.putExtra("url_content", url_content);
        intent.putExtra("cookieFlag", cookieFlag);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onDestroy() {
        if(webView!=null){
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}