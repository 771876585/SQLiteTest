package com.yezh.sqlite.sqlitetest.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * https://blog.csdn.net/lly347705530/article/details/78612587
 * Created by yezh on 2018/9/7 15:01.
 */

public class FallingView extends View {

    private Context mContext;
    private AttributeSet mAttrs;

    private int viewWidth;
    private int viewHeight;

    private static final int defaultWidth = 600; //默认宽度
    private static final int defaultHeight = 1000; //默认高度
    private static final int intervalTime = 10; //重绘间隔时间

    private Paint paint;
    private int snowY;

    public FallingView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        //STROKE 描边；FILL 填充；FILL_AND_STROKE 描边加填充
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10F);
        snowY = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);

        viewWidth = width;
        viewHeight = height;
    }

    private int measureSize(int defaultSize, int measureSpec){
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else if(specMode == MeasureSpec.AT_MOST){
            result = Math.min(result, specSize);
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100, snowY, 25, paint);
        //间隔一段时间重新绘制
        //getHandler().postDelayed(runnable, intervalTime);

        canvas.drawPoint(500, 500, paint); //在坐标（200，200）位置绘制一个点
        canvas.drawPoints(new float[]{ //绘制一组点
                600, 600, 600, 700, 600, 800
        }, paint);

        canvas.drawLine(400, 400, 400, 600, paint); //在坐标（300， 300）（400，600）之间绘制一条线
        canvas.drawLines(new float[]{
                200, 200, 300, 200,
                300, 300, 400, 300
        }, paint);

        //绘制矩形
        canvas.drawRect(500, 200, 700, 400, paint);
        //精度不同
        Rect rect = new Rect(500, 200, 700, 400);
        canvas.drawRect(rect, paint);
        RectF rectF = new RectF(500, 200, 700, 400);
        canvas.drawRect(rectF, paint);

        //绘制圆角矩形
        RectF rectF1 = new RectF(800, 200, 1000, 400);
        canvas.drawRoundRect(rectF1, 30, 30, paint);

        //Api21以上才支持
       // canvas.drawRoundRect(800, 200, 1000, 400, 30, 30, paint);

        //椭圆
        RectF rectF2 = new RectF(800, 500, 1000, 600);
        canvas.drawOval(rectF2, paint);

        //圆
        canvas.drawCircle(800, 1000, 200, paint);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            snowY += 15;
            if(snowY > viewHeight){ //超出屏幕位置，则重置位置
                snowY = 0;
            }
            invalidate();
        }
    };
}
