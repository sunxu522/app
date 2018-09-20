package com.fanday.arch.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fanday.arch.R;
import com.fanday.arch.common.base.BaseActivity;
import com.fanday.arch.library.utils.Kits;
import com.fanday.arch.ui.ToastUtil;
import com.fanday.arch.ui.edittext.ClearEditText;
import com.fanday.arch.ui.edittext.PwdEditText;
import com.fanday.arch.ui.edittext.SmsEditText;
import com.fanday.arch.ui.edittext.ValidateWather;
import com.fanday.arch.ui.textstyleplus.ClickListener;
import com.fanday.arch.ui.textstyleplus.StyleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phoneNum)
    ClearEditText phoneNum;
    @BindView(R.id.smsCode)
    SmsEditText smsCode;
    @BindView(R.id.newPwd)
    PwdEditText newPwd;
    @BindView(R.id.rePwd)
    PwdEditText rePwd;
    @BindView(R.id.invite_phone)
    SmsEditText invitePhone;
    @BindView(R.id.register)
    AppCompatButton register;
    @BindView(R.id.agreement)
    TextView agreement;
    @BindView(R.id.identity)
    RadioGroup identity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        //设置邀请人cell
        setInviteCell();

        identity.check(R.id.personal);

        //设置协议显示文字和颜色
        new StyleBuilder().addTextStyle("注册即表示同意")
                .textColor(Kits.UIKits.getColor(R.color.light_gray))
                .commit().addTextStyle("《企巴特易货交易注册协议》")
                .textColor(Kits.UIKits.getColor(R.color.color_e60516))
                .click(new ClickListener() {
                    @Override
                    public void click(String text) {
                        // TODO: 2018/9/20  点击协议
                        ToastUtil.showShort("打开协议");
                    }
                }).commit()
                .show(agreement);

    }

    @Override
    protected void initListener() {
        ValidateWather wather = new ValidateWather(ValidateWather.TYPE.PHONE,
                register,phoneNum,smsCode.getSmsEdit(),newPwd,rePwd);
        wather.watch();
    }

    private void setInviteCell() {
        invitePhone.setLeftIcon(Kits.UIKits.getDrawable(R.drawable.icon_login_invite));
        Button button = invitePhone.getRightButton();
        button.setText("选填");
        button.setTextColor(Kits.UIKits.getColor(R.color.light_gray));
        invitePhone.getSmsEdit().setHint("请输入推荐人手机号");
        button.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.register)
    public void onViewClicked() {
    }
}
