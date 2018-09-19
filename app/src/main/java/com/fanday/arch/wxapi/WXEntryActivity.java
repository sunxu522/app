package com.fanday.arch.wxapi;

import android.os.Bundle;

import com.fanday.arch.R;
import com.fanday.arch.common.base.App;
import com.fanday.arch.ui.dialog.LoadingDialog;
import com.fanday.arch.ui.ToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.umeng.weixin.callback.WXCallbackActivity;

/**
 * Created by Administrator on 2017/5/9.
 */

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_wxentry);
        App.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        finish();
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
                        String code = ((SendAuth.Resp) baseResp).code.trim(); //即为所需的code
                        // TODO: 2018/9/19 授权成功，进行登录
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        ToastUtil.showShort("分享成功");
                        break;
                    default:
                        break;
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录
                        ToastUtil.showShort("登录取消");
                        // TODO: 2018/9/19 取消登录了
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        ToastUtil.showShort("分享取消");
                        break;
                    default:
                        break;
                }

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录
                        ToastUtil.showShort("登录被拒绝,请联系客服");
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享
                        ToastUtil.showShort("分享被拒绝,请联系客服");
                        break;
                    default:
                        break;
                }
                break;
            default:
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录
                        ToastUtil.showShort("登录失败,请联系客服");
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享
                        ToastUtil.showShort("分享失败,请联系客服");
                        break;
                    default:
                        break;
                }
                break;
        }

    }

}
