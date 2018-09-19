package com.fanday.arch.ui.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.fanday.arch.R;
import com.fanday.arch.library.utils.Kits;

public class SmsEditText extends FrameLayout {

    private Button getCode;
    public SmsEditText(@NonNull Context context) {
        super(context);
    }

    public SmsEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_get_sms_code, null);
        this.addView(view);
        getCode = (Button) view.findViewById(R.id.get_code);
        getCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
            }
        });
    }

    /**
     * 获取editText输入的验证码
     * @return
     */
    public String getSmsCode(){
        EditText editText = (EditText) this.findViewById(R.id.sms_code);
        return editText.getText().toString();
    }

    /**
     * 设置左边的icon
     * @param drawable
     */
    public void setLeftIcon(Drawable drawable){
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
        EditText editText = (EditText) this.findViewById(R.id.sms_code);
        editText.setCompoundDrawables(drawable,null,null,null);
    }

    private CountDownTimer timer = new CountDownTimer(60*1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setEnabled(false);
            getCode.setText((millisUntilFinished / 1000) + "秒");
            getCode.setTextColor(Kits.UIKits.getColor(R.color.gray));
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText("获取验证码");
            getCode.setTextColor(Kits.UIKits.getColor(R.color.color_eb4a56));
        }
    };


    public SmsEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
