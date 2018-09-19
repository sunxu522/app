package com.fanday.arch.library.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fanday.arch.library.utils.Kits;

public class AutoImageLoader {

    private AutoImageLoader() {
    }

    public static AutoImageLoader get() {
        return Holer.instance;
    }

    /**
     * @param context
     * @param loader
     * @param img
     */
    public<T> void loadRes(final Context context, DrawableTypeRequest<T> loader, final ImageView img) {
        loader.asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                float scale = resource.getHeight() * 1.00f / (resource.getWidth() * 1.00f);
                ViewGroup.LayoutParams lp = img.getLayoutParams();
                lp.height = (int) (Kits.Dimens.getScreenWidth(context) * scale);
                img.setLayoutParams(lp);
                img.setImageBitmap(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
//                img.setBackgroundDrawable(errorDrawable);
            }
        });
    }

    public void load(final Context context, String url, final ImageView img) {
        DrawableTypeRequest<String> loader = Glide.with(context).load(url);
        loadRes(context, loader, img);
    }

    public void load(final Context context, int resId, final ImageView img) {
        DrawableTypeRequest<Integer> loader = Glide.with(context).load(resId);
        loadRes(context, loader, img);
    }


    private static class Holer {
        private static AutoImageLoader instance = new AutoImageLoader();
    }
}
