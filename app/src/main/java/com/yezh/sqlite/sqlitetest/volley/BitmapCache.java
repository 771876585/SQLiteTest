package com.yezh.sqlite.sqlitetest.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by yezh on 2018/9/27 15:16.
 */

public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> lruCache;
    //最大缓存大小
    private int max = 10*1024*1024;

    public BitmapCache(){
        lruCache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String s) {
        return lruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        lruCache.put(s, bitmap);
    }
}
