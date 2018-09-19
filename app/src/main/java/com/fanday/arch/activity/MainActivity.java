package com.fanday.arch.activity;

import com.fanday.arch.R;
import com.fanday.arch.bean.Person;
import com.fanday.arch.common.base.BaseActivity;
import com.fanday.arch.interactor.net.API;
import com.fanday.arch.interactor.net.JsonCallBack;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
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
                },true);
    }
}
