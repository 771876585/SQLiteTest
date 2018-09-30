package com.yezh.sqlite.sqlitetest.volleyokhttp3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by yezh on 2018/9/29 17:58.
 */

public class MyStringRequest extends StringRequest {
    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
