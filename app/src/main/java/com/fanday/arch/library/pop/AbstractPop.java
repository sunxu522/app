package com.fanday.arch.library.pop;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public abstract class AbstractPop {
    protected PopupWindow mPopWindow;
    protected View contentView;

    public AbstractPop(Context context){
        contentView = getView(context);
        this.mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        if (Build.VERSION.SDK_INT >= 24){
            Rect visibleFrame = new Rect();
            contentView.getGlobalVisibleRect(visibleFrame);
            int height = contentView.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            mPopWindow.setHeight(height);
        }
    }

    protected abstract View getView(Context context);

    public void showAsDropDown(View anchor){
        mPopWindow.showAsDropDown(anchor);
        doAnim(contentView);
    }

    protected void doAnim(View contentView){}
}
