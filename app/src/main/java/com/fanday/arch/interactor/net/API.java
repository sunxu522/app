package com.fanday.arch.interactor.net;

import com.lzy.okgo.OkGo;

import java.util.Map;

public class API {

    /**
     * @param url  请求地址
     * @param data  请求数据
     * @param callBack  回调
     * @param showLoading 是否显示loading
     * @param <T>
     */
    public static<T> void post(String url,Object data,JsonCallBack<T> callBack,boolean showLoading){
        if(showLoading)
            callBack.onBegin();
        post(url,data,callBack);
    }
    public static<T> void post(String url,Object data,JsonCallBack<T> callBack){
        OkGo.<T>post(url).tag(callBack.getTag())
                .upString(data.toString())
                .execute(callBack);
    }



    public static<T> void get( String url, Map<String, String> params, JsonCallBack<T> callBack,boolean showLoading){
        if(showLoading)
            callBack.onBegin();
        get(url,params,callBack);
    }
    public static<T> void get( String url, Map<String, String> params, JsonCallBack<T> callBack){
        OkGo.<T>get(url).tag(callBack.getTag())
                .params(params)
                .execute(callBack);
    }



    public static<T> void get( String url, JsonCallBack<T> callBack){
        OkGo.<T>get(url).tag(callBack.getTag())
                .execute(callBack);
    }


    public static void cancel(Object tag){
        OkGo.getInstance().cancelTag(tag);
    }
}
