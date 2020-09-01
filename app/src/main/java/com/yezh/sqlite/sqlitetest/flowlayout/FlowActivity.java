package com.yezh.sqlite.sqlitetest.flowlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yezh.sqlite.sqlitetest.R;

public class FlowActivity extends AppCompatActivity {

    private String mName[] = {"Welcome", "android", "TextView",
    "apple", "1111111111111", "22222222222222", "3333", "44444", "55",
    "type", "logcat", "name"};
    private XCFlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        initChildViews();
    }

    private void initChildViews() {
        mFlowLayout = findViewById(R.id.flowLayout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for(int i = 0; i < mName.length; i++){
            TextView view = new TextView(this);
            view.setText(mName[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
            mFlowLayout.addView(view, lp);
        }
    }
}