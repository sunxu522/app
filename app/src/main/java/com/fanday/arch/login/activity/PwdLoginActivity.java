package com.fanday.arch.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanday.arch.R;
import com.fanday.arch.bean.Person;
import com.fanday.arch.common.base.BaseActivity;
import com.fanday.arch.interactor.net.API;
import com.fanday.arch.interactor.net.JsonCallBack;
import com.fanday.arch.library.third.ThirdLogin;
import com.fanday.arch.ui.edittext.ClearEditText;
import com.fanday.arch.ui.edittext.PwdEditText;
import com.fanday.arch.ui.edittext.ValidateWather;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PwdLoginActivity extends BaseActivity {

    @BindView(R.id.phoneNum)
    ClearEditText phoneNum;
    @BindView(R.id.pwd)
    PwdEditText pwd;
    @BindView(R.id.pwdLogin)
    Button pwdLogin;
    @BindView(R.id.smsLogin)
    TextView smsLogin;
    @BindView(R.id.foundPwd)
    TextView foundPwd;
    @BindView(R.id.qqLogin)
    ImageView qqLogin;
    @BindView(R.id.wxLogin)
    ImageView wxLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pwd_login;
    }

    @Override
    protected void initListener() {
        ValidateWather wather = new ValidateWather(ValidateWather.TYPE.PHONE,
                pwdLogin,phoneNum,pwd);
        wather.watch();
    }

    @Override
    protected void initData() {
        API.get("http://www.wanandroid.com/banner/json",
                new HashMap<String, String>(),
                new JsonCallBack<List<Person>>(this) {
                    @Override
                    public void onSuccess(List<Person> person) {
                        person = null;
                    }
                }, true);

    }


    @OnClick({R.id.pwdLogin, R.id.smsLogin, R.id.foundPwd, R.id.qqLogin, R.id.wxLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pwdLogin:
                break;
            case R.id.smsLogin:
                startActivityAndFinished(SmsLoginActivity.class);
                break;
            case R.id.foundPwd:
                startActivity(ForgetPwdActivity.class);
                break;
            case R.id.qqLogin:
                ThirdLogin.get().qqLogin(this, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.wxLogin:
                ThirdLogin.get().wxLogin();
                break;
        }
    }
}
