package com.fanday.arch.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanday.arch.ui.dialog.LoadingDialog;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 海航
 * 2018/9/17
 * 功能描述:
 */

public abstract class BaseFragment extends Fragment implements BaseView{
    protected Activity mActivity;
    protected Context mContext;
    protected View mRootView;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
        loadingDialog = createLoadingDialog();
    }

    public LoadingDialog createLoadingDialog() {
        if (loadingDialog != null)
            return loadingDialog;
        return new LoadingDialog(mActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;

    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initView();
        initData();
        setListener();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if(mImmersionBar != null) mImmersionBar.init();
            refresh();
        }
    }

    private void refresh() {

    }

    protected abstract int getLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
//        mImmersionBar = ImmersionBar.with(this).statusBarColor(fragmentThemeColor()).fitsSystemWindows(false).keyboardEnable(true);
//        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();

        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }


    /**
     * view与数据绑定
     */

    protected void initView(){}


    /**
     * 设置监听
     */

    protected void setListener(){}

    /**
     * 初始化数据
     */

    protected abstract void initData();


    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showDialog(String msg) {

    }

    public void startActivity(Class<?> activity) {
        Intent i = new Intent(getActivity(), activity);
        mContext.startActivity(i);
    }

    /**
     * 跳转到其他的Activity并且携带数据
     *
     * @param clazz
     * @param key
     * @param value
     */
    protected void startActivityWithExtra(Class clazz, String key, Serializable value) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra(key, value);
        mContext.startActivity(intent);
    }

    public void startActivity(Class<?> activity, String key, String value) {
        Intent i = new Intent(getActivity(), activity);
        i.putExtra(key, value);
        mContext.startActivity(i);
    }

}
