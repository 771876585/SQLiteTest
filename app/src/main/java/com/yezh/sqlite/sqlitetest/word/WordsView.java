package com.yezh.sqlite.sqlitetest.word;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yezh on 2018/9/19 16:35.
 */

public class WordsView extends View {

    /*绘制的列表导航字母*/
    private String words[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private int choose = -1;

    private TextView textDialog;

    float singleHeight;

    //字母画笔
    private Paint wordsPaint = new Paint();

    private onTouchChangedListener onTouchChangedListener;

    public void setOnTouchChangedListener(WordsView.onTouchChangedListener onTouchChangedListener) {
        this.onTouchChangedListener = onTouchChangedListener;
    }

    //设置，可以改变右边字母，保证都可以选择
    public void setWords(List<String> words){
        this.words = (String[])words.toArray(new String[words.size()]);
        invalidate();

    }

    public void setTextDialog(TextView textDialog){
        this.textDialog = textDialog;
    }

    public WordsView(Context context) {
        super(context);
    }

    public WordsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int warp_width = height / 27 * words.length;

        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(warp_width, warp_width);
        }else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(warp_width, height);
        }else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width, warp_width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(); //获取对应宽度
        int height = getHeight();//获取对应高度
        //获取每一个字母的高度
        singleHeight = (height * 1f) / words.length;
        singleHeight = (height * 1f - singleHeight / 2) / words.length;
        for(int i = 0; i < words.length; i++){
            wordsPaint.setAntiAlias(true);
            wordsPaint.setTextSize(150 * getWidth() / 320);
            //判断是不是我们按下的当前字母
            if(choose == i){
                wordsPaint.setColor(Color.BLUE);
            }else{
                wordsPaint.setColor(Color.GRAY);
            }

            //x坐标等于中间--字符串宽度的一半
            float xPos = width / 2 - wordsPaint.measureText(words[i]) / 2;
            float yPos = singleHeight * i + singleHeight;

            canvas.drawText(words[i], xPos, yPos, wordsPaint);
            wordsPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float y = event.getY(); //点击Y坐标
        int c = (int) (y / getHeight() * words.length);//点击y坐标所占总高度的比例*数组的长度就等于点击数组中的个数
        switch (action){
            case MotionEvent.ACTION_UP:
                //choose = -1;
                invalidate();
                if(textDialog != null){
                    textDialog.setVisibility(INVISIBLE);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.e("ccc", c+ "");
                if(choose != c){
                    if(c >= 0 && c < words.length){
                        if(onTouchChangedListener != null){
                            onTouchChangedListener.touchChanged(words[c]);
                        }
                        if(textDialog != null){
                            textDialog.setText(words[c]);
                            textDialog.setVisibility(VISIBLE);
                            //动态改变dialog位置
                            int right = textDialog.getLeft();
                            textDialog.setX(right / 2 * 3);
                            textDialog.setY(singleHeight * c);
//                            if(c > 24){
//                                textDialog.setY(singleHeight * 24);
//                            }else{
//                                textDialog.setY(singleHeight * c);
//                            }
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void setTouchIndex(String word){
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word)){
                choose = i;
                invalidate();
                return;
            }
        }
    }

    public interface onTouchChangedListener{
        void touchChanged(String s);
    }
}
