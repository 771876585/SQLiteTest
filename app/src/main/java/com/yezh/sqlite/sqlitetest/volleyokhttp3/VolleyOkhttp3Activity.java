package com.yezh.sqlite.sqlitetest.volleyokhttp3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yezh.sqlite.sqlitetest.R;

import okhttp3.OkHttpClient;

public class VolleyOkhttp3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_okhttp3);

        volleyGet();
    }

    /**
     * get请求
     */
    private void volleyGet(){
        RequestQueue queue = Volley3.newRequestQueueByClient(this, new OkHttpClient());
        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13266594342";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //s 为请求返回的字符串数据
                        Toast.makeText(VolleyOkhttp3Activity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(VolleyOkhttp3Activity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        //设置请求的tag标签，可以在全局请求队列中通过tag标签进行请求查找
        request.setTag("testGet");
        queue.add(request);
    }
}
