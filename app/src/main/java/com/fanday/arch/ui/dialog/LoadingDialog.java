package com.fanday.arch.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fanday.arch.R;

public class LoadingDialog extends AbstractDialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected View getView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_loading, null);
    }

    @Override
    protected void doAnim(View v) {
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.imgv_diaglog);
        // 加载动画
        Animation currentLoadingAnimation = AnimationUtils.loadAnimation(v.getContext(), R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(currentLoadingAnimation);
    }
}
