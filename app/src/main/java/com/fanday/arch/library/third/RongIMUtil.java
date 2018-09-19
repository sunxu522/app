package com.fanday.arch.library.third;

import android.content.Context;

import com.fanday.arch.interactor.Cons;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.SystemUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;


/**
 * Created by fanday on 2017/8/23.
 */

public class RongIMUtil {

    public static void toCustomerChat(final Context context, String token) {
        //确保在主进程（应用进程）才进行连接tcp
        if (context.getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(context.getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    //首先需要构造使用客服者的用户信息
                    CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                    CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();
                    RongIM.getInstance().startCustomerServiceChat(context, Cons.ThirdAppKey.RONG_CUSTOMER_SERVICE_ID, "在线客服", csInfo);
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }
}
