package com.fanday.arch.ui;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.fanday.arch.R;
import com.fanday.arch.common.base.App;

/**
 * Created by fanday on 2017/3/15.
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 显示短提示
     *
     * @param value
     */
    public static void showShort(String value) {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), value, Toast.LENGTH_SHORT);
            toast.getView().setBackgroundResource(R.drawable.toast_bg);
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }
        toast.setText(value);
        toast.show();
    }

    /**
     * 显示长提示
     *
     * @param value
     */
    public static void showLong(String value) {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), value, Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.drawable.toast_bg);
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }
        toast.setText(value);
        toast.show();
    }


}
