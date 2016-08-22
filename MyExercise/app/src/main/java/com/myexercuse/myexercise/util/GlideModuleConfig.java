package com.myexercuse.myexercise.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;


/**
 * Created by jiajiewang on 16/3/23.
 */
public class GlideModuleConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 100 * 1024 * 1024));
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "MY_CACHE_LOCATION", 100 * 1024 * 1024));
//        builder.setMemoryCache(new LruResourceCache(100*1024*1024));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "MY_CACHE_LOCATION", 100 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //glide.clearDiskCache();
        glide.setMemoryCategory(MemoryCategory.NORMAL);
    }
}
