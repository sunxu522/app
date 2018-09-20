package com.fanday.arch.login.activity;

import com.fanday.arch.R;
import com.fanday.arch.common.base.BaseActivity;

import butterknife.OnClick;

public class SetPwdSuccessActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd_success;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        finish();
    }
}
