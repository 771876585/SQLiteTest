package com.yezh.sqlite.sqlitetest.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.yezh.sqlite.sqlitetest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yezh on 2018/9/7 14:28.
 */

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ApiService apiService = RetrofitUtils.getInstance().getApiService(CONTENTS.BASE_URL, ApiService.class);
        Call<POWeather> weatherCall = apiService.getWeather("广州", "JSON", "FK9mkfdQsloEngodbFl4FeY3");
        weatherCall.enqueue(new Callback<POWeather>() {
            @Override
            public void onResponse(Call<POWeather> call, Response<POWeather> response) {
                POWeather weather = response.body();

                ((TextView)findViewById(R.id.text)).setText(weather.getStatus());
                Toast.makeText(WeatherActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<POWeather> call, Throwable t) {

            }
        });
    }
}
