package com.fanday.arch.ui.edittext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.fanday.arch.R;

public class PwdEditText extends AppCompatEditText {
    /**
     * 删除按钮的引用
     */
    private Drawable visibleDrawable;
    private Drawable inVisibleDrawable;
    /**
     * 控件是否有焦点
     */

    private boolean isShow ;


    public PwdEditText(Context context) {
        this(context, null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        this.setTransformationMethod(isShow ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
        this.setMaxLines(1);
        this.setPadding(this.getPaddingLeft()!=0?this.getPaddingLeft():0,
                this.getPaddingTop()!=0?this.getPaddingTop():0,
                this.getPaddingRight()!=0?this.getPaddingRight():0,
                this.getPaddingBottom()!=0?this.getPaddingBottom():0);
        this.setEllipsize(TextUtils.TruncateAt.END);
        visibleDrawable=getResources().getDrawable(R.drawable.icon_pwd_show);
        visibleDrawable.setBounds(0, 0, visibleDrawable.getIntrinsicWidth(), visibleDrawable.getIntrinsicHeight());

        inVisibleDrawable=getResources().getDrawable(R.drawable.icon_pwd_hide);
        inVisibleDrawable.setBounds(0, 0, inVisibleDrawable.getIntrinsicWidth(), inVisibleDrawable.getIntrinsicHeight());
        setPwdIcon(inVisibleDrawable);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable) {
                isShow = !isShow;
                this.setTransformationMethod(isShow ? HideReturnsTransformationMethod.getInstance()
                        : PasswordTransformationMethod.getInstance());
                setPwdIcon(isShow?visibleDrawable:inVisibleDrawable);
                this.setSelection(this.getText().length());
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     */
    protected void setPwdIcon(Drawable drawable) {
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
    }


}
