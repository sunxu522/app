package com.fanday.arch.ui.textstyleplus;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.widget.TextView;

import com.fanday.arch.library.utils.Logs;

import java.util.ArrayList;
import java.util.List;

/**
 * ClickListener <br/>
 * Created by fanday
 */
public class StyleBuilder {

    private List<ISpannable> items;
    private SpannableStringBuilder builder;

    public StyleBuilder() {
        items = new ArrayList<>();
        builder = new SpannableStringBuilder();
    }

    StyleBuilder addStyleItem(ISpannable item) {
        items.add(item);

        return this;
    }

    public TextStyleBuilder addTextStyle(String text) {
        return new TextStyleBuilder(this, text);
    }

    public ImageStyleBuilder addImageStyle(String text) {
        return new ImageStyleBuilder(this, text);
    }

    public StyleBuilder text(String text) {

        return addTextStyle(text).commit();
    }

    public StyleBuilder newLine() {
        return text("\n");
    }

    public void show(TextView textView) {
        Context context = textView.getContext();

        for (ISpannable item : items) {
            if (builder != null)
                builder.append(item.makeSpannableString(context));
        }

        textView.setText(builder);

        addLinkMovementMethod(textView);

        Logs.i("text = ", textView.getText().toString());
    }

    /**
     * Add the movement method to handle the clicks.
     */
    private void addLinkMovementMethod(TextView textView) {
        MovementMethod m = textView.getMovementMethod();

        boolean isTouchableMovementMethod = m instanceof TouchableMovementMethod;

        if (m == null || !isTouchableMovementMethod) {
            if (textView.getLinksClickable()) {
                textView.setMovementMethod(TouchableMovementMethod.getInstance());
            }
        }
    }
}