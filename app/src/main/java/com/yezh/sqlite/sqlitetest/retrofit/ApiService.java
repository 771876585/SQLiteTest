package com.yezh.sqlite.sqlitetest.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yezh on 2018/9/7 14:11.
 */

public interface ApiService {

    /**
     * 无参数get请求
     * http://service.meiyinkeqiu.com/service/ads/cptj
     * @return
     */
    @GET("service/ads/cptj")
    Call<News> getNoParams();

    /**
     * 有参数get请求
     * 拼接参数/形式
     * https://api.github.com/users/baiiu
     * @param user
     * @return
     */
    @GET("users/{user}")
    Call<User> getHasParams(@Path("user") String user);

    /**
     * http://www.93.gov.cn/93app/data.do
     * 拼接 ? &
     * @param channelId
     * @param startNum
     * @return
     */
    @GET("data.do")
    Call<Party> getHasParams2(@Query("channelId") int channelId, @Query("startNum") int startNum);

    /**
     * post请求 http://120.27.23.105/user/reg 注册
     * @param mobile
     * @param password
     * @return
     */
    @POST("reg")
    @FormUrlEncoded //支持表单提交
    Call<User> register(@Field("mobile") String mobile, @Field("password") String password);

    /**
     * 实例，查询天气
     * @param location
     * @param output
     * @param ak
     * @return
     */
    @GET("telematics/v3/weather")
    Call<POWeather> getWeather(@Query("location") String location, @Query("output") String output, @Query("ak") String ak);
}
