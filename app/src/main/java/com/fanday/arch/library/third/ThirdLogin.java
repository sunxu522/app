package com.fanday.arch.library.third;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.fanday.arch.common.base.App;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;

public class ThirdLogin {

    public static ThirdLogin get(){
        return Holder.instance;
    }


    /**
     * 微信登录
     * 回调在 WXEntryActivity
     */
    public void wxLogin(){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test_neng";
        App.iwxapi.sendReq(req);
    }

    /**
     * qq登陆
     * @param activity
     * @param loginListener 回调接口
     */
    public void qqLogin(Activity activity,IUiListener loginListener){
        String scope = "all";
        App.mTencent.login(activity, scope, loginListener);
    }

    public void qqLogin(Fragment fragment, IUiListener loginListener){
        String scope = "all";
        App.mTencent.login(fragment, scope, loginListener);
    }


    private ThirdLogin(){}
    private static class Holder{
        static ThirdLogin instance = new ThirdLogin();
    }




    /*        IUiListener loginListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
            }
            public void onComplete(Object value) {
                if (value == null) {
                    return;
                }
                JSONObject json = (JSONObject) value;
                HashMap<String, String> params = new HashMap<>();
                String token = null;
                String openid = null;
                try {
                    token = json.getString("access_token");
                    openid = json.getString("openid");
                    params.put("access_token", json.getString("access_token"));
                    // TODO Auto-generated method stub
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
            }
        };*/
}
