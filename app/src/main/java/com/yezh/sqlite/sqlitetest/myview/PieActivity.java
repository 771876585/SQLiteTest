package com.yezh.sqlite.sqlitetest.myview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yezh.sqlite.sqlitetest.R;

import java.util.ArrayList;

/**
 * Created by yezh on 2018/9/7 18:00.
 */

public class PieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        PieView pieView = (PieView)findViewById(R.id.pie);
        ArrayList<PieData> pieDatas = new ArrayList<>();
        pieDatas.add(new PieData("语文", 10));
        pieDatas.add(new PieData("数学", 80));
        pieDatas.add(new PieData("英语", 100));
        pieDatas.add(new PieData("物理", 50));
        pieDatas.add(new PieData("化学", 30));
        pieView.setData(pieDatas);
    }
}
