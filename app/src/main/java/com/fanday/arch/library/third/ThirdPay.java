package com.fanday.arch.library.third;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.fanday.arch.common.base.App;
import com.fanday.arch.library.utils.ThreadPoolManager;
import com.fanday.arch.ui.ToastUtil;
import com.fanday.arch.library.third.alipayutils.OrderInfoUtil2_0;
import com.fanday.arch.library.third.alipayutils.PayResult;
import com.tencent.connect.dataprovider.Constants;
import com.tencent.mm.sdk.modelpay.PayReq;

import java.util.Map;

public class ThirdPay {
    public static ThirdPay get() {
        return ThirdPay.Holder.instance;
    }

    /**
     * 微信支付
     * @param appid 信息由提交订单给服务端后返回
     * @param partnerid
     * @param prepayid
     * @param noncestr
     * @param timestamp
     * @param sign
     */
    public void wxpay(String appid, String partnerid, String prepayid, String noncestr
            , String timestamp, String sign) {
        PayReq request = new PayReq();
        request.appId = appid;
        request.partnerId = partnerid;
        request.prepayId = prepayid;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = noncestr;
        request.timeStamp = timestamp;
        request.sign = sign;
        App.iwxapi.sendReq(request);
    }


    //私钥
    private String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCRSmIreEOapHohT9BSkdnB6WQu71LK2WAWEBdvpoUjqp2g7fmGqxqG72rVfS/voxt11H6biXQpjN6Q2yD5r22pjE9TeoNbjcr3hs+9eALSeO/w7zZu7nK4A84tOC1ebOKYs8olCn0M9C0Ah4wvtSkWsV44K4XQCqSSe7gSHM9VCl7kW/CQ/LuKLPbKpD1tRFHyB1LmS0NAD+4bPxIp9IrDALDfK5Td7FIM5yPGICHS/kneAub+5MjpqtuwCrnN+fLh/8KVNYeDdgZGR7d4trYNeZIcHWW5DtkoQuaUmLrVJ7WyztgB/eeGHrDoyTmG248rJkwAoogVGiVZnCu3Q0+bAgMBAAECggEBAI/FDl27lRIqndvm0dtTDisvW36CPegSsF1EsRw/qFHB3FdKBXf4Sripo4r8ZPn56gtwKZSZsMQI7Jhk7j2FFbwH5ttRSTjIl8bWtSzk284P9EbzP2cIN6bVxSjEBUkB8Dxw9KAwv8uYuAtlFhiDaNRW0Ug98R0L2wxEtsHdlmCr8jztYrbkZ+fLQ2F2GM/afCFh82eKC1hNqWfXpXFCoSEgy9a9JtQ09gCAIRi2lVb0a0dwxf594+XlrIwj0+0YMPc9ttUqqE+kG9cqJwMbnJmUCAAhsqh1pDxqKHdxap0tdDAJORIi1orHDlsyO4n2pjat9OBorgLxr1ZSa6H1W7kCgYEA0wYv3EGkBnw1CVRy6tnQgh8zaL+7EEDTOqOFkmrDuFYj9aNyk+7xI0pfQR+So5t3fU94+w7Z0d85n1Zf0jW27d2FHhfhfpaiqH2MgqWvwZqUPojjH7Ayf+i/ypR2YXT3HT2+kwdA9uJ5Q4FcF3c0b4uCIwmzb4Q5GLyZVoHeNRcCgYEAsEGqrm10Ja4hUQjnaYjvX3ZOQ77PIH5g5ZxkkDFKDYxN8C7enwWiAO1ujEGJhIbfWkXhc7cWrwHo1OsXp4TjDzBDHdcn51y4WgRg0MgTBgjCs1hS9k5eCI+N4/eZoX2PHQA7PdtR9rdKZK7WoJsP9vqOQc0GZoDMUuACWPSKlB0CgYAGnfAMQQaqqWRkWYCM+q1+Fwpeo3xzIwU8lnoOwEhI1HKmNizWlKx9Bcz81sebV7mkdUR+4tVTrmM5KtUwWzzUMpkE+4y3knOhQuaQBi//qyAw4cv1Z2n99sdky8j2f1tsUE5Uf+q6kxloU3sWxUcgpEg9XwWAXq4Hp4pLWyEjmwKBgQCskb6xRU0gz1qjYppgg8gKqWR7g8h8QgBRD0yOuROOLD7q2JjzDarOSamg/VHNRdhNJHpWt34oHzB9tyfTxpIhftlX0B6rseZ1jC/Weu3VTKKEwgeITLZYn2UHPJdYu/xxIpbeO2wGqPLXnS9a1DdPU4YQ+tI3bgefZOAnhFZiKQKBgQDF13wtPWyQBge+WW2U1OcXgIn91EKRJP0qsvmPVz7uO/HZ5deJp47A5pZ0iqbkgzVDNFZlXlzLS2BXuxJICaWj4mOkkMsxt3v2xvZu2dvpg8Kk8gziP2tDn3fkZ0YjCkq0YbtcAUCa4t51eGu36OEDOHjc0iRRhxl+LFvWuqQElw==";

    /**
     * @param activity
     * @param orderInfo 服务端返回的订单信息
     * @param order_sn  服务端返回的订单签名
     */
    public void alipay(final Activity activity, final String orderInfo, String order_sn) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constants.APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA2_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                final Map<String, String> result = alipay.payV2(orderInfo, true);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handlerResult(result);
                    }
                });
            }
        });

    }

    private void handlerResult(Map<String, String> result) {
        PayResult payResult = new PayResult(result);
        /**
         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
         */
        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
        String resultStatus = payResult.getResultStatus();
        // 判断resultStatus 为9000则代表支付成功
        if (TextUtils.equals(resultStatus, "9000")) {
            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
            ToastUtil.showShort("支付成功");
            // TODO: 2018/9/19

        } else {
            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
            ToastUtil.showShort("支付失败");
        }
    }


    private ThirdPay() {
    }

    private static class Holder {
        static ThirdPay instance = new ThirdPay();
    }
}
