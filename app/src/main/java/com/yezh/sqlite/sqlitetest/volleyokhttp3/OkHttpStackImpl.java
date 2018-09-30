package com.yezh.sqlite.sqlitetest.volleyokhttp3;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yezh on 2018/9/29 16:55.
 */

public class OkHttpStackImpl implements OkHttpStack {

    private final OkHttpClient mClient;
    public OkHttpStackImpl(OkHttpClient client){
        mClient = client;
    }

    private void setConnectionParametersForRequest(okhttp3.Request.Builder builder, Request<?> request) throws IOException, AuthFailureError{
        switch (request.getMethod()){
            case Request.Method.DEPRECATED_GET_OR_POST:
                byte[] postBody = request.getPostBody();
                if(postBody != null){
                    builder.post(RequestBody.create(MediaType.parse(request.getPostBodyContentType()), postBody));
                }
                break;
            case Request.Method.GET:
                builder.get();
                break;
            case Request.Method.DELETE:
                builder.delete();
                break;
            case Request.Method.POST:
                builder.post(createRequestBody(request));
                break;
            case Request.Method.PUT:
                builder.post(createRequestBody(request));
                break;

        }
    }

    /**
     * 如果需要对OkHttp的请求体进行修改或者添加一些内容可以在这个地方修改
     * @param request
     * @return
     * @throws AuthFailureError
     */
    protected RequestBody createRequestBody(Request request) throws AuthFailureError {
        final byte[] body = request.getBody();
        if(body == null) return null;
        return RequestBody.create(MediaType.parse(request.getBodyContentType()), body);
    }

    @Override
    public Response performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {

        int timeoutMs = request.getTimeoutMs();
        OkHttpClient client = mClient.newBuilder()
                .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .connectTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .build();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        Map<String, String> headers = request.getHeaders();
        for(final String name : headers.keySet()){
            builder.addHeader(name, headers.get(name));
        }
        for(final String name : map.keySet()){
            builder.addHeader(name, map.get(name));
        }

        setConnectionParametersForRequest(builder, request);

        okhttp3.Request okhttp3Request = builder.url(request.getUrl()).build();
        Response response = client.newCall(okhttp3Request).execute();

        return response;
    }
}
