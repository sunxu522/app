package com.fanday.arch.login.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fanday.arch.R;
import com.fanday.arch.common.base.BaseFragment;
import com.fanday.arch.library.utils.Kits;
import com.fanday.arch.login.activity.SetPwdSuccessActivity;
import com.fanday.arch.ui.edittext.ClearEditText;
import com.fanday.arch.ui.edittext.PwdEditText;
import com.fanday.arch.ui.edittext.SmsEditText;
import com.fanday.arch.ui.edittext.ValidateWather;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgetPwdFragment extends BaseFragment {
    @BindView(R.id.phoneNum)
    ClearEditText phoneNum;
    @BindView(R.id.email)
    ClearEditText email;
    @BindView(R.id.smsCode)
    SmsEditText smsCode;
    @BindView(R.id.newPwd)
    PwdEditText newPwd;
    @BindView(R.id.rePwd)
    PwdEditText rePwd;
    @BindView(R.id.submit)
    AppCompatButton submit;
    Unbinder unbinder;

    //手机找回密码
    public static final int FORGET_PWD_PHONE = 0;
    //邮箱找回密码
    public static final int FORGET_PWD_EMAIL = 1;
    public static final String TYPE = "type";
    private int type = FORGET_PWD_PHONE;

    public static BaseFragment newInstance(int type) {
        ForgetPwdFragment fragment = new ForgetPwdFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_pwd;
    }

    @Override
    protected void initView() {
        type = getArguments().getInt(TYPE, FORGET_PWD_PHONE);
        phoneNum.setVisibility(type == FORGET_PWD_PHONE ? View.VISIBLE : View.GONE);
        email.setVisibility(type == FORGET_PWD_EMAIL ? View.VISIBLE : View.GONE);
        smsCode.setLeftIcon(Kits.UIKits.getDrawable(R.drawable.icon_login_safe));

    }

    @Override
    protected void initListener() {
        ValidateWather wather = null;
        if(type==FORGET_PWD_PHONE){
            wather = new ValidateWather(ValidateWather.TYPE.PHONE,
                    submit,phoneNum,smsCode.getSmsEdit(),newPwd,rePwd);
        }else{
            wather = new ValidateWather(ValidateWather.TYPE.EMAIL,
                    submit,email,smsCode.getSmsEdit(),newPwd,rePwd);
        }
        wather.watch();
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.submit)
    public void onViewClicked() {
        startActivity(SetPwdSuccessActivity.class);
        mActivity.finish();
    }
}
