package com.yezh.sqlite.sqlitetest.myvideoplayer.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezh.sqlite.sqlitetest.R;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void tinyWindow(View view) {
        startActivity(new Intent(this, TinyWindowPlayActivity.class));
    }

    public void videoList(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void changeClarity(View view) {
        startActivity(new Intent(this, ChangeClarityActivity.class));
    }

    public void useInFragment(View view) {
        startActivity(new Intent(this, UseInFragActivity.class));
    }

    public void processHomeKeyInActivity(View view) {
        // 在Activity中使用NiceVideoPlayer，如果需要处理播放时按下Home键的逻辑.
        startActivity(new Intent(this, ProcessHome1Activity.class));
    }

    public void processHomeKeyInFragment(View view) {
        // 在Fragment中使用NiceVideoPlayer，如果需要处理播放时按下Home键的逻辑.
        startActivity(new Intent(this, ProcessHome2Activity.class));
    }
}
