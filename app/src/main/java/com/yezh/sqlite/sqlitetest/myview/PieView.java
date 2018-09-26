package com.yezh.sqlite.sqlitetest.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by yezh on 2018/9/7 17:40.
 */

public class PieView extends View {

    //颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    //初始绘制角度
    private float mStartAngle = 0;
    private ArrayList<PieData> mData;

    private int mWidth, mHeight;

    private Paint mPaint = new Paint();


    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == mData){
            return;
        }

        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2); //将画布坐标原点移动到中心位置
        float r = (float)(Math.min(mWidth, mHeight) / 2 * 0.8); //饼状图半径
        RectF rectF = new RectF(-r, -r, r, r); //绘制区域

        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);
            currentStartAngle += pie.getAngle();
        }

    }

    //设置起始角度
    public void setStartAngle(int mStartAngle){
        this.mStartAngle = mStartAngle;
        invalidate(); //刷新
    }

    //设置数据
    public void setData(ArrayList<PieData> mData){
        this.mData = mData;
        initData(mData);
        invalidate();
    }

    private void initData(ArrayList<PieData> mData){
        if(null == mData || mData.size() == 0){
            return;
        }

        float sumVaule = 0;
        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            sumVaule += pie.getValue(); //计算数值和
            int j = i % mColors.length; //设置颜色
            pie.setColor(mColors[j]);
        }
        float sumAngle = 0;
        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            float percentage = pie.getValue() / sumVaule; //百分比
            float angle = percentage * 360; //对应的角度
            pie.setPercentage(percentage);
            pie.setAngle(angle);
            sumAngle += angle;
        }
    }
}
