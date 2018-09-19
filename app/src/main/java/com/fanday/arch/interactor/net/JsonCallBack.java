package com.fanday.arch.interactor.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.fanday.arch.common.base.BaseView;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class JsonCallBack<T> extends AbsCallback {

    private StringConvert convert;
    private WeakReference<BaseView> refView;

    public JsonCallBack(BaseView view) {
        convert = new StringConvert();
        this.refView = new WeakReference<>(view);
    }

    public Object getTag() {
        return this.refView.get();
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        String body = convert.convertResponse(response);
        //包装请求体
        JSONObject jsonRes = new JSONObject(body);
        //校验服务端返回信息
        validate(jsonRes);
        //获取data字段转成对象
        String data = jsonRes.optString("data", "");
        T t = null;
        if (!TextUtils.isEmpty(data))
            t = JSON.parseObject(data, getType());
        response.close();
        return t;
    }

    /**
     * 校验code 等参数是否响应成功
     *
     * @param jsonRes
     */
    private void validate(JSONObject jsonRes) {

    }

    /**
     * 获取泛型参数
     *
     * @return
     */
    public Type getType() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return type;
    }

    @Override
    public void onSuccess(Response response) {
        dismiss();
        onSuccess((T) response.body());
    }

    /**
     * 成功回调
     *
     * @param t
     */
    public abstract void onSuccess(T t);


    @Override
    public void onError(Response response) {
        super.onError(response);
        errorDoing();
    }

    /**
     * 失败需要做的事情
     */
    protected void errorDoing() {
        dismiss();
    }

    public void onBegin() {
        show();
    }

    private void show() {
        if (this.refView.get() != null) this.refView.get().showLoading();
    }

    private void dismiss() {
        if (this.refView.get() != null) this.refView.get().dismissLoading();
    }
}
