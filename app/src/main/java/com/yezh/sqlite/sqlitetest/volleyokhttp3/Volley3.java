package com.yezh.sqlite.sqlitetest.volleyokhttp3;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by yezh on 2018/9/29 17:37.
 */

public class Volley3 {
    public Volley3(){

    }

    public static RequestQueue newRequestQueue(Context context, OkHttpStack stack){
        File cacheDir = new File(context.getCacheDir(), "volley");
        if(stack == null){
            stack = new OkHttpStackImpl(new OkHttpClient().newBuilder().build());
        }

        BasicNetwork3 network3 = new BasicNetwork3(stack);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network3);
        queue.start();
        return queue;
    }

    /**
     ** 创建一个指定请求客户端的请求队列
     ** @param context 上下文
     ** @param client OkHttpClient
     ** @return 请求队列
     **/
    public static RequestQueue newRequestQueueByClient(Context context, OkHttpClient client) {
        return newRequestQueue(context, new OkHttpStackImpl(client));
    }
    /**
     ** 创建一个默认请求客户端的请求队列
     ** @param context 上下文
     ** @return
     **/
    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, null);
    }

}
