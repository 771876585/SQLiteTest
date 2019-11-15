package com.yezh.sqlite.sqlitetest.pdfview;


import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yezh on 2019/11/15 10:53.
 * Description：
 */
public class WaterMarkView extends View {
    private String name = "123456789";
    private String waterMark;
    public WaterMarkView(Context context) {
        super(context);
        initWaterMark();
    }

    public WaterMarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWaterMark();
    }

    public WaterMarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWaterMark();
    }

    private void initWaterMark(){
        waterMark = "机密文件，拷贝必究" + "\r\n" + name;
    }

    public void setUserName(String userName){
        this.name = userName;
    }

    //开始绘制水印
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得页面尺寸
        int width=getWidth();
        int height=getHeight();
        //TextPaint是paint的子类，该类可以很方便的进行文字的绘制
        TextPaint textPaint = new TextPaint();
        textPaint.setARGB(0x80, 0, 0, 0);//设置水印颜色
        textPaint.setTextSize(20.0F);//设置水印字体大小
        textPaint.setAntiAlias(true); // 抗锯齿

        //参数意义分别为：文字内容、TextPaint对象、文本宽度、对齐方式、行距倍数、行距加数和是否包含内边距。
        //这里比较重要的地方是设置文本宽度，当文本宽度比这个值大的时候就会自动换行。
        StaticLayout layout = new StaticLayout(waterMark, textPaint,width,
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

        float addWidth=36.0f,addHeight=24.0f;
        //水印的位置
        float[] x = new float[]{width / 4-addWidth , width * 3 / 4-addWidth, width / 4-addWidth,  width* 3 / 4-addWidth};
        float[] y = new float[]{height / 4-addHeight, height  / 4-addHeight, height*3 / 4-addHeight, height * 3 / 4-addHeight};
        //页面上绘制四个水印
        for (int i = 0; i < 4; i++) {
            canvas.save();
            canvas.translate(x[i], y[i]);
            canvas.rotate(15);
            layout.draw(canvas);
            canvas.restore();
        }
    }

}
