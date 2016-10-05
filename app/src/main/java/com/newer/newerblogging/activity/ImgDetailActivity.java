package com.newer.newerblogging.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.bean.microblog.MicroblogPic;
import com.newer.newerblogging.utils.Config;

import java.util.ArrayList;

import butterknife.Bind;

public class ImgDetailActivity extends BaseActivity {

    @Bind(R.id.iv_image_detail)
    ImageView ivImageDetail;

    private ArrayList<MicroblogPic> mMicroblogPics;
    private int mIndex = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_img_detail;
    }

    @Override
    public void initListener() {
        //ImageView的滑动事件
        ivImageDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //手指按下
                        x1 = event.getX();
                        break;

                    case MotionEvent.ACTION_UP: //手指松开，然后计算距离
                        float x2 = event.getX();

                        //向右滑动
                        if (x2 - x1 >= 20) {
                            mIndex--;
                            if (mIndex < 0) {
                                mIndex = 0;
                                Toast.makeText(ImgDetailActivity.this, "已是第一张", Toast.LENGTH_SHORT).show();
                            }
                        } else if (x1 - x2 >= 20) { //向左滑动
                            mIndex++;
                            if (mIndex == mMicroblogPics.size()) {
                                Toast.makeText(ImgDetailActivity.this, "已是最后一张", Toast.LENGTH_SHORT).show();
                                mIndex = mMicroblogPics.size() - 1;
                            }
                        } else {
                            ImgDetailActivity.this.finish();
                        }

                        showImage();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        //获得传递过来的图片位置
        mIndex = intent.getIntExtra(Config.IMAGE_POSITION, 0);
        //所有图片
        mMicroblogPics = intent.getParcelableArrayListExtra(Config.IMAGE_LIST);

        showImage();
    }

    private void showImage() {
        String imgUrl = mMicroblogPics.get(mIndex).getThumbnail_pic().replace("thumbnail", "large");
//        if (imgUrl.endsWith("gif")) {
//            Glide.with(this)
//                    //中等图片 bmiddle
//                    //大图片 large
//                    //中等图片和large图片api接口中没有提供，但看json数据可以得到
//                    //如果把thumbnail字符串换成上面的那两个字符串，就可以了
//                    .load(imgUrl)
//                    .asGif()
//                    .into(ivImageDetail);
//        } else {
//            Glide.with(this)
//                    //中等图片 bmiddle
//                    //大图片 large
//                    //中等图片和large图片api接口中没有提供，但看json数据可以得到
//                    //如果把thumbnail字符串换成上面的那两个字符串，就可以了
//                    .load(imgUrl)
//                    .asBitmap()
//                    .into(ivImageDetail);
//        }
        Glide.with(this)
                .load(imgUrl)
                //加载进磁盘中，加快gif格式的图片的加载速度
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivImageDetail);

        setTitle((mIndex + 1) + "/" + mMicroblogPics.size());
    }

    float x1 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //如果点击的是图片之外的区域，则关闭Activity
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.finish();
        }

        return true;
    }
}
