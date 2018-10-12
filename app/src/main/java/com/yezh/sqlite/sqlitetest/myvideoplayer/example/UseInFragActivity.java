package com.yezh.sqlite.sqlitetest.myvideoplayer.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yezh.sqlite.sqlitetest.R;
import com.yezh.sqlite.sqlitetest.myvideoplayer.NiceVideoPlayerManager;


public class UseInFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_in_frag);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new DemoFragenment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
