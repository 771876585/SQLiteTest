package com.yezh.sqlite.sqlitetest.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yezh on 2018/9/7 14:03.
 */

public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;
    private static Retrofit retrofit;

    public RetrofitUtils(){

    }

    public static RetrofitUtils getInstance(){
        if(retrofitUtils == null){
            synchronized (RetrofitUtils.class){
                if(retrofitUtils == null){
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }

        return retrofitUtils;
    }

    public static synchronized Retrofit getRetrofit(String url){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){

            @Override
            public void log(String message) {

            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .connectTimeout(5000, TimeUnit.SECONDS).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public <T>T getApiService(String url, Class<T> tClass){
        Retrofit retrofit = getRetrofit(url);
        return retrofit.create(tClass);
    }
}
