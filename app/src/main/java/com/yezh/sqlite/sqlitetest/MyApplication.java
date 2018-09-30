package com.yezh.sqlite.sqlitetest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yezh on 2018/9/27 14:33.
 */

public class MyApplication extends Application {

    private static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueues(){
        return queue;
    }
}
