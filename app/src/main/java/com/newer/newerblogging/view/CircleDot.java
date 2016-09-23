package com.newer.newerblogging.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Chalmers on 2016-09-05 21:06.
 * email:qxinhai@yeah.net
 */
public class CircleDot extends View {

    /** 字体颜色 */
    private int mTextColor = Color.WHITE;
    /** 背景颜色 */
    private int mCircleColor = Color.RED;

    /** 视图的宽 */
    private int mWidth;
    /** 视图的高 */
    private int mHeight;

    /** 圆心坐标 */
    private int mX;
    private int mY;
    /** 圆的半径 */
    private int mRadius;

    /** 显示的字 */
    private String mText = "5";
    /** 字体大小 */
    private int mTextSize;
    /** 字体宽度 */
    private int mStrokeWidth = 5;
    /** 字体画笔 */
    private Paint mPaintText;
    /** 背景画笔 */
    private Paint mPaintCircle;

    private boolean isInitData = true;

    public CircleDot(Context context) {
        this(context,null);
    }

    public CircleDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initData();

        mTextSize = mRadius;
        mPaintText.setTextSize(mTextSize);

        canvas.drawCircle(mX,mY,mRadius,mPaintCircle);
        canvas.drawText(mText,mX,mY+mTextSize/3,mPaintText);
    }

    private void initPaint(){
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintCircle.setColor(mCircleColor);
        mPaintCircle.setStrokeWidth(mStrokeWidth);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(mTextColor);
        mPaintText.setStrokeWidth(mStrokeWidth);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    private void initData(){
        if(!isInitData){
            return ;
        }

        mWidth = getWidth();
        mHeight = getHeight();

        mX = mWidth / 2 - mStrokeWidth;
        mY = mHeight / 2 - mStrokeWidth;
        mRadius = (mX > mY ? mY : mX) - mStrokeWidth;

        isInitData = false;
    }

    public void setData(int num){
        mText = String.valueOf(num);

        invalidate();
    }
}
