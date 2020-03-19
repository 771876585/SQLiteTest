package com.yezh.sqlite.sqlitetest.musicplayer;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yezh.sqlite.sqlitetest.R;

/**
 * Created by yezh on 2020/3/19 15:08.
 * Description：
 */
public class GaussianBlurActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaussian);
        ImageView gaussian_iv = findViewById(R.id.gaussian_iv);
        ImageView gaussian_iv2 = findViewById(R.id.gaussian_iv2);
        gaussian_iv.setBackground(GaussianBlurUtil.BoxBlurFilter(BitmapFactory.decodeResource(getResources(), R.drawable.face)));
        gaussian_iv2.setImageBitmap(GaussianBlurUtil.BoxBlurFilterBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.face)));
//        gaussian_iv.setImageBitmap(GaussianBlurUtil.drawableToBitmap(ContextCompat.getDrawable(this, R.drawable.face)));
    }

    @Override
    public void onClick(View v) {

    }
}
