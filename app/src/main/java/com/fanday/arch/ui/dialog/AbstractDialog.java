package com.fanday.arch.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.fanday.arch.R;

public abstract class AbstractDialog {

    protected Dialog dialog;
    protected View contentView;

    public AbstractDialog(Context context){
        this(context,R.style.loading_dialog);
    }

    public AbstractDialog(Context context, int themeResId){
        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = getView(inflater);// 得到加载view
        dialog = new Dialog(context, themeResId);// 创建自定义样式dialog
        dialog.setCancelable(true);// 不可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);// 设置外部不点击
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_hui_normal);//将dialog窗体的背景框设为透明
        dialog.setContentView(contentView);// 设置布局
    }

    public void show(){
        doAnim(contentView);
        dialog.show();
    }

    public void cancel(){
        dialog.cancel();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void setOnDismissListener(@Nullable DialogInterface.OnDismissListener listener){
        dialog.setOnDismissListener(listener);
    }



    protected void doAnim(View v) {

    }

    protected abstract View getView(LayoutInflater inflater);

}
