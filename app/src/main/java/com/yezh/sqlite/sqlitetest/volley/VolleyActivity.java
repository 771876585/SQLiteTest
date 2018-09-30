package com.yezh.sqlite.sqlitetest.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.yezh.sqlite.sqlitetest.MyApplication;
import com.yezh.sqlite.sqlitetest.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    ImageView image;
    String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13266594342";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
//        volleyGet();
//        volleyGet2();
        volleyPost();
//        volleyPost2();
        image = (ImageView)findViewById(R.id.test_img);
//        loadImageByVolley();
        loadImageWithCache();
        volleyXML();
    }

    /**
     * get请求
     */
    private void volleyGet(){

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //s 为请求返回的字符串数据
                        Toast.makeText(VolleyActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(VolleyActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        //设置请求的tag标签，可以在全局请求队列中通过tag标签进行请求查找
        request.setTag("testGet");
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * JsonObjectRequest 和JsonArrayRequest同理
     */
    private void volleyGet2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //jsonObject为请求返回的Json格式数据
                        Toast.makeText(VolleyActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(VolleyActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) ;
        request.setTag("testGet");
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 重写Request类的getParams（）方法
     */
    private void volleyPost(){
        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(VolleyActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", "13266594342");
                return map;
            }
        };

        request.setTag("testPost");
        MyApplication.getHttpQueues().add(request);

    }

    private void volleyPost2(){
        String url = "http://www.kuaidi100.com/query";
        Map<String,String> map = new HashMap<>();
        map.put("type","yuantong");
        map.put("postid","229728279823");
        //将map转化为JSONObject对象
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(VolleyActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        request.setTag("testPost");
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 通过volley加载网络图片
     */
    private void loadImageByVolley(){
        String url = "http://pic20.nipic.com/20120409/9188247_091601398179_2.jpg";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        image.setImageResource(R.mipmap.ic_launcher);
                    }
                });
        request.setTag("loadImage");
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 使用ImageLoader加载及缓存网络图片
     */
    private void loadImageWithCache(){
        String url = "http://pic20.nipic.com/20120409/9188247_091601398179_2.jpg";
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());
        ImageLoader.ImageListener listener = loader.getImageListener(image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(url, listener);
    }

    /**
     * volley解析xml
     */
    private void volleyXML(){
        String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        final XMLRequest request = new XMLRequest(Request.Method.GET, url,
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        try {
                            int eventType = response.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT){
                                switch (eventType){
                                    case XmlPullParser.START_TAG:
                                        String nodeName = response.getName();
                                        if("city".equals(nodeName)){
                                            //city节点下的第一个属性名称和对应的值
                                            String pName = response.getAttributeName(0);
                                            String pValue = response.getAttributeValue(0);
                                            Log.e("city", pName+":"+pValue);
                                        }
                                        break;
                                }
                                eventType = response.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        request.setTag("testxml");
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("testPost");
        MyApplication.getHttpQueues().cancelAll("loadImage");
        MyApplication.getHttpQueues().cancelAll("testxml");
    }
}
