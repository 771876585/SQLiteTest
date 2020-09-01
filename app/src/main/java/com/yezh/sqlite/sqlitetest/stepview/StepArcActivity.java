package com.yezh.sqlite.sqlitetest.stepview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yezh.sqlite.sqlitetest.R;

public class StepArcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_arc);
        StepArcView stepArcView = findViewById(R.id.arc_view);
        stepArcView.setCurrentCount(7000, 2000);
    }
}