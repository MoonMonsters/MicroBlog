package com.newer.newerblogging.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;

/**
 * Created by Chalmers on 2016-09-11 19:04.
 * email:qxinhai@yeah.net
 */

/**
 * 根据url从网上获得Bitmap对象（图片），使用Glide可以缓存
 */
public class GlideForPicFromNet {

    /**
     * 从网上获得头像
     * @param context 上下文对象
     * @param url 头像所在的url
     * @param width ImageView的宽，根据getLayoutParams.width获得
     * @param height ImageView的高，根据getLayoutParams.height获得
     * @param headCallback 回调方法
     */
    public static void netGetHeadWithUrl(final Context context, final String url, final int width, final int height,
                                         final HeadCallback headCallback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                try {
                    //使用根据url从网上获得头像
                    bitmap = Glide.with(context)
                            .load(url)
                            .asBitmap()
                            //传递进来的是ImageView的未处理大小
                            .into(width, height)
                            .get();
                }catch (Exception e){
                    //如果加载失败，则使用默认头像
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher);
                }

                final Bitmap finalBitmap = bitmap;
                //因为不能在主线程中修改控件的显示，所以在这个位置就放到主线程中去传递
                //在使用该方法时，就可以简单操作
                ((BaseActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headCallback.doCallbackData(finalBitmap);
                    }
                });
            }
        }).start();
    }

    public interface HeadCallback{
        void doCallbackData(Bitmap bitmap);
    }

}
