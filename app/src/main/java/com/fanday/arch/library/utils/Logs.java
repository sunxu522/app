package com.fanday.arch.library.utils;

import android.util.Log;

import com.fanday.arch.BuildConfig;

public class Logs {
    private static boolean isDebug = BuildConfig.DEBUG;

    public static void e(String tag, String content) {
        if (isDebug) Log.e(tag, content);
    }

    public static void i(String tag, String content) {
        if (isDebug) Log.i(tag, content);
    }

    public static void v(String tag, String content) {
        if (isDebug) Log.v(tag, content);
    }
}
