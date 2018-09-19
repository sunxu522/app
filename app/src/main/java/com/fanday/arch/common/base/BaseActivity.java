package com.fanday.arch.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fanday.arch.R;
import com.fanday.arch.library.dialog.LoadingDialog;
import com.fanday.arch.library.net.API;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by 海航
 * 2017/12/15
 * 功能描述:
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
        setContentView(getLayoutId());
        loadingDialog = createLoadingDialog();

        unbinder = ButterKnife.bind(this);
        //状态栏沉浸式处理
        initStatueBar();
        initView();
        initListener();
        initData();
        WinManager.get().addActivity(this);//将Activity添加到堆栈
    }

    public LoadingDialog createLoadingDialog() {
        if (loadingDialog != null)
            return loadingDialog;
        return new LoadingDialog(this);
    }

    /**
     * 对状态栏做沉浸式处理
     */
    protected void initStatueBar() {
//        ImmersionBar.with(this).fitsSystemWindows(true).keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).statusBarColor(activityThemeColor()).init();
        //在BaseActivity里初始化
        ImmersionBar.with(this).init();
    }


    /**
     * 界面主布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件和监听器,一般的控件初始化、监听器初始化和网络请求都应该从这里开始
     */
    protected abstract void initView();

    protected void initListener() {
    }

    /**
     * 联网加载数据,填充数据等
     */
    protected abstract void initData();

    /**
     * 基本初始化
     */
    protected void initBase() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        WinManager.get().removeActivity(this);

        //关闭正在执行的请求
        API.cancel(this);
    }


    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        //loadingDialog.dismiss();
    }

    @Override
    public void showDialog(String msg) {

    }

    /**
     * 跳转到其他的Activity
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }


    /**
     * 跳转到其他的Activity并且结束当前的Activity
     *
     * @param clazz
     */
    protected void startActivityAndFinished(Class clazz) {
        startActivity(clazz);
        finish();
    }

    /**
     * 跳转到其他的Activity并且携带数据
     *
     * @param clazz
     * @param key
     * @param value
     */
    protected void startActivityWithExtra(Class clazz, String key, Serializable value) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * 携带数据跳转到其他的Activity，并且结束当前Activity
     *
     * @param clazz
     * @param key
     * @param value
     */
    protected void startActivityWithExtraAndFinished(Class clazz, String key, Serializable value) {
        startActivityWithExtra(clazz, key, value);
        finish();
    }
}
