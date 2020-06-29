package com.yezh.sqlite.sqlitetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yezh.sqlite.sqlitetest.bitmapcache.BitmapCacheActivity;
import com.yezh.sqlite.sqlitetest.musicplayer.GaussianBlurActivity;

/**
 * Created by yezh on 2020/3/19 15:08.
 * Description：
 */
public class NextPaperActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Button gaussianBlur = findViewById(R.id.gaussianBlur);
        gaussianBlur.setOnClickListener(this);
        findViewById(R.id.cacheBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gaussianBlur:
                startActivity(new Intent(this, GaussianBlurActivity.class));
                break;
            case R.id.cacheBtn:
                startActivity(new Intent(this, BitmapCacheActivity.class));
                break;

                default:
                    break;
        }
    }
}