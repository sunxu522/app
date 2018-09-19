package com.fanday.arch.library.third;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.fanday.arch.R;
import com.fanday.arch.common.base.App;
import com.fanday.arch.ui.dialog.LoadingDialog;
import com.fanday.arch.ui.ToastUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ThirdShare {
    public static ThirdShare get() {
        return Holder.instance;
    }

    /**
     * 微信分享
     *
     * @param context 上下文
     * @param title   标题
     * @param content 内容
     * @param type    0--好友  1--朋友圈
     * @param url     分享链接地址
     */
    public static void shareWeChat(final Context context, String title, String content, int type, String url) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = bmpToByteArray(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "img";

        req.message = msg;
        req.scene = type;
        App.iwxapi.sendReq(req);
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * QQ分享
     *
     * @param activity
     * @param title
     * @param content
     * @param imageUrl    图片链接
     * @param contentLink 内容链接
     */
    public static void shareToQQ(final Activity activity, String title, String content, String imageUrl, String contentLink) {
        final LoadingDialog loading = new LoadingDialog(activity);
        loading.show();
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, contentLink);// 内容地址
        if (TextUtils.isEmpty(imageUrl)) {
            // TODO: 2018/9/19 默认图片
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称

        }
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
        // 分享操作要在主线程中完成
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                App.mTencent.shareToQQ(activity, params, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        loading.dismiss();
                        ToastUtil.showShort("分享成功");
                    }

                    @Override
                    public void onError(UiError uiError) {
                        loading.dismiss();
                        ToastUtil.showShort("分享失败");
                    }

                    @Override
                    public void onCancel() {
                        loading.dismiss();
                        ToastUtil.showShort("取消分享");
                    }
                });
            }

        });
    }


    public static void shareToQZone(final Activity activity, String title, String content, String imageUrl, String contentLink) {
        final LoadingDialog loading = new LoadingDialog(activity);
        loading.show();
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, contentLink);// 内容地址
        ArrayList<String> imgUrlList = new ArrayList<>();
        // TODO: 2018/9/19 添加要分享的图片url
        imgUrlList.add("http://gushimima.oss-cn-beijing.aliyuncs.com/120.png");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        // 分享操作要在主线程中完成
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                App.mTencent.shareToQzone(activity, params, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        loading.dismiss();
                        ToastUtil.showShort("分享成功");
                    }

                    @Override
                    public void onError(UiError uiError) {
                        loading.dismiss();
                        ToastUtil.showShort("分享失败");
                    }

                    @Override
                    public void onCancel() {
                        loading.dismiss();
                        ToastUtil.showShort("取消分享");
                    }
                });
            }
        });
    }


    private ThirdShare(){}
    private static class Holder {
        static ThirdShare instance = new ThirdShare();
    }

}
