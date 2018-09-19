package com.fanday.arch.ui.titlebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.fanday.arch.R;


public class CommonTitleBar extends TitleBar {
    public CommonTitleBar(Context context) {
        super(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setBackgroundResource(R.color.color_f44f37);
        this.setLeftImageResource(R.drawable.icon_back_arrow);
        this.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        this.setTitleColor(Color.WHITE);
    }
}
