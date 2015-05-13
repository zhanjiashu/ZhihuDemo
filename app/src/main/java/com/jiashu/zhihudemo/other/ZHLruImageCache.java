package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Jiashu on 2015/5/5.
 * 为 Volley 的 图片缓存，缓存大小为应用最大占用内存的 1/8
 */
public class ZHLruImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public ZHLruImageCache(int maxSize) {
        super(maxSize);
    }

    public ZHLruImageCache(Context context) {
        this(getDefaultCacheSize(context));
    }

    private static int getDefaultCacheSize(Context context) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String s) {
        return null;
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {

    }
}
