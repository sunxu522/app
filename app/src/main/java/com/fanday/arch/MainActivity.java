package com.fanday.arch;

import com.fanday.arch.common.base.BaseActivity;
import com.fanday.arch.library.net.API;
import com.fanday.arch.library.net.JsonCallBack;

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
