package com.yezh.sqlite.sqlitetest.volleyokhttp3;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import java.io.IOException;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by yezh on 2018/9/29 15:49.
 */

public interface OkHttpStack  {

    /**
     *
     * @param request volley中请求
     * @param map 参数
     * @return okhttp3请求响应
     * @throws IOException
     * @throws AuthFailureError
     */
    Response performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError;

}
