package com.fanday.arch.library.loader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * fanday
 */

public class GlideCache implements GlideModule {

    public static final String GLIDE_CARCH_DIR = "imgCache";
    public static final int GLIDE_CATCH_SIZE = 1024 * 1024 * 250;


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //获取系统分配给应用的总内存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设置图片内存缓存占用八分之一
        int memoryCacheSize = maxMemory / 8;
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        //设置BitmapPool(图片池)缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));

        //自定义磁盘缓存目录路径与大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                GLIDE_CARCH_DIR,
                GLIDE_CATCH_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
