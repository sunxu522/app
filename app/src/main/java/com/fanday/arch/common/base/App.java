package com.fanday.arch.common.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fanday.arch.interactor.Cons;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tauth.Tencent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;



/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 * Created by 海航
 * 2017/9/26
 * 功能描述:程序入口
 */

public class App extends Application {
    private static App instance;
    public static IWXAPI iwxapi;
    public static Tencent mTencent;

    @Override
    public void onCreate() {
        super.onCreate();
        //防止多进程导致多次初始化
        if (getPackageName().equals(QbSdk.getCurrentProcessName(this))) {
            init();
        }

    }

    private void init() {
        instance = this;
        initUM();
        initX5();
        initOkGo();
        initWXAndQQ();
        RongIM.init(this);
    }

    private void initWXAndQQ() {
        //初始化微信
        iwxapi = WXAPIFactory.createWXAPI(this, Cons.ThirdAppKey.WX_APP_ID, true);
        iwxapi.registerApp(Cons.ThirdAppKey.WX_APP_ID);
        //初始化qq
        mTencent = Tencent.createInstance(Cons.ThirdAppKey.QQ_AppID, this);
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                           //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    private void initUM() {
    }

    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    @NonNull
    public static App getInstance() {
        return instance;
    }
}
