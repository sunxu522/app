package com.fanday.arch.login.activity;

import android.support.v4.app.FragmentTransaction;

import com.fanday.arch.R;
import com.fanday.arch.common.base.BaseActivity;
import com.fanday.arch.common.base.BaseFragment;
import com.fanday.arch.login.fragment.ForgetPwdFragment;
import com.fanday.arch.ui.tabs.TabsLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ForgetPwdActivity extends BaseActivity {
    List<BaseFragment> fragments;
    @BindView(R.id.tabs_bar)
    TabsLayout tabsBar;

    private int currentIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        fragments.add(ForgetPwdFragment.newInstance(ForgetPwdFragment.FORGET_PWD_PHONE));
        fragments.add(ForgetPwdFragment.newInstance(ForgetPwdFragment.FORGET_PWD_EMAIL));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, fragments.get(0))
                .add(R.id.fl_container, fragments.get(1))
                .hide(fragments.get(1))
                .show(fragments.get(0)).commit();
        currentIndex = 0;
    }

    @Override
    protected void initListener() {
        tabsBar.setOnTabSelectListener(new TabsLayout.OnTabSelectListener() {
            @Override
            public void onSelect(int index) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(fragments.get(currentIndex)).show(fragments.get(index)).commit();
                currentIndex = index;
            }
        });
    }

    @Override
    protected void initData() {

    }

}
