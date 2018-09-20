package com.fanday.arch.ui.edittext;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.fanday.arch.library.utils.Kits;

public class ValidateWather implements TextWatcher {
    private TYPE firstType;
    private Button button;
    private EditText[] edts;

    public enum TYPE{
        PHONE,EMAIL
    }

    public ValidateWather(TYPE firstType, Button button, EditText... edts) {
        this.firstType = firstType;
        this.button = button;
        this.edts = edts;
    }

    public void watch() {
        for (EditText et : edts) {
            et.addTextChangedListener(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean complete = validate();
        if(complete){
            button.setEnabled(true);
        }else {
            button.setEnabled(false);
        }
    }

    private boolean validate() {
        for (int i = 0; i < edts.length; i++) {
            String content = edts[i].getText().toString();
            if (i == 0) {
                 if(firstType == TYPE.PHONE){
                     boolean isMobile = Kits.Validator.isMobile(content);
                     if(!isMobile)return false;
                 }else if(firstType == TYPE.EMAIL){
                     boolean isEmail = Kits.Validator.isEmail(content);
                     if(!isEmail)return false;
                 }
            }
            if(TextUtils.isEmpty(content))return false;
        }
        return true;
    }
}
