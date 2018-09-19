package com.fanday.arch.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.fanday.arch.common.base.App;
import com.fanday.arch.ui.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.errCode == 0) { //支付成功
            ToastUtil.showShort("支付成功");
            // TODO: 2018/9/19 支付成功

        } else if (baseResp.errCode == -2) { //用户取消支付
            ToastUtil.showShort("支付取消");
        } else if (baseResp.errCode == -1) {  //支付出现错误
            ToastUtil.showShort("支付未成功，请联系客服");
        }
        finish();

    }


}