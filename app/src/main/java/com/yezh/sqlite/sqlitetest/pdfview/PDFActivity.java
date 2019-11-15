package com.yezh.sqlite.sqlitetest.pdfview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.yezh.sqlite.sqlitetest.R;

/**
 * Created by yezh on 2018/9/10 16:44.
 */

public class PDFActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        try {
            pdfView.fromAsset("123.pdf").defaultPage(1)
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            // 当用户在翻页时候将回调。
                            Toast.makeText(getApplicationContext(), page + " / " + pageCount, Toast.LENGTH_SHORT).show();
                        }
                    }).load();
        }catch (Exception e){
            Toast.makeText(this, "文件不存在或已损坏", Toast.LENGTH_SHORT).show();
            finish();
        }

        WaterMarkView markView = (WaterMarkView)findViewById(R.id.water_mark);
        markView.setUserName("PDF名称");

    }
}
