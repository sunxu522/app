package com.fanday.arch.library.loader.glide;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 *fanday
 */
public class GlideHelper {
    private static GlideHelper instance;
    public static GlideHelper getInstance(){
        if(instance==null){
            synchronized (GlideHelper.class) {
                if(instance==null){
                    instance=new GlideHelper();
                }
            }
        }
        return instance;
    }

    public void load(Context context,String path,ImageView imageView){
            Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).
                    crossFade().into(imageView);
    }


}