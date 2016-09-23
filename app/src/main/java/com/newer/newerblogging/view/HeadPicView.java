package com.newer.newerblogging.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Chalmers on 2016-09-11 13:57.
 * email:qxinhai@yeah.net
 */

/**
 * 绘制圆形头像类
 */
public class HeadPicView extends ImageView{

    private Bitmap mBitmap = null;

    private int mWidth;
    private int mHeight;

    public HeadPicView(Context context) {
        this(context,null);
    }

    public HeadPicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //避免空指针
        if(mBitmap == null){
            return ;
        }

        mWidth = getWidth();
        mHeight = getHeight();

        //得到圆形的Bitmap对象
        Bitmap bitmap = toRoundBitmap(mBitmap);

        Rect src = new Rect(0,0,mWidth,mHeight);
        Rect dest = new Rect(0,10 ,mWidth,mHeight);

        //将图像绘制出来
        canvas.drawBitmap(bitmap,src,dest,null);
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap
     *            传入Bitmap对象
     * @return 圆形的Bitmap对象
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {

        //设置截取的图片大小
        //在图片大小和控件大小中，选择较小值
        int width = mWidth;
        int height = mHeight;

        if(width > bitmap.getWidth()){
            width = bitmap.getWidth();
        }
        if(height > bitmap.getHeight()){
            height = bitmap.getHeight();
        }

        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    /**
     * 设置Bitmap图片
     * @param bm 传进来的Bitmap对象
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = bm;

        //刷新界面
        invalidate();
    }
}