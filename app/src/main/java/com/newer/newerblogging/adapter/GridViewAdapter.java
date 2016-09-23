package com.newer.newerblogging.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.ImgDetailActivity;
import com.newer.newerblogging.base.BaseViewHolder;
import com.newer.newerblogging.bean.microblog.MicroblogPic;
import com.newer.newerblogging.utils.Config;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Chalmers on 2016-09-13 14:43.
 * email:qxinhai@yeah.net
 */
public class GridViewAdapter extends BaseAdapter {

    private ArrayList<MicroblogPic> mMicroblogPics;
    private Context mContext;

    public GridViewAdapter(Context context, ArrayList<MicroblogPic> microblogPics){
        this.mContext = context;
        this.mMicroblogPics = microblogPics;
    }

    @Override
    public int getCount() {

        return mMicroblogPics.size();
    }

    @Override
    public Object getItem(int position) {
        return mMicroblogPics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_img,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(mMicroblogPics.get(position));

        return convertView;
    }

    class ViewHolder extends BaseViewHolder {

        @Bind(R.id.iv_gridview_img)
        ImageView ivGridViewImg;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindData(final Object obj) {
            MicroblogPic pic = (MicroblogPic) obj;

            Glide.with(mContext)
                    .load(pic.getThumbnail_pic())
                    .asBitmap()
                    .into(ivGridViewImg);
            //中等图片 bmiddle
            //大图片 large
            //中等图片和large图片api接口中没有提供，但看json数据可以得到
            //如果把thumbnail字符串换成上面的那两个字符串，就可以了
//            String url = pic.getThumbnail_pic().replace("thumbnail","large");
//
//            bindListener(url);
            bindListener(pic);
        }

        private void bindListener(final MicroblogPic pic){
            ivGridViewImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImgDetailActivity.class);
                    //把图片的url传递到另一个Activity中去
                    intent.putExtra(Config.IMAGE_LIST,mMicroblogPics);
                    intent.putExtra(Config.IMAGE_POSITION,mMicroblogPics.indexOf(pic));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
