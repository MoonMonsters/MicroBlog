package com.newer.newerblogging.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.CreateCommentActivity;
import com.newer.newerblogging.activity.RepostActivity;
import com.newer.newerblogging.activity.RepostWeiboDetailActivity;
import com.newer.newerblogging.activity.SingleWeiboDetailActivity;
import com.newer.newerblogging.activity.UserHomeActivity;
import com.newer.newerblogging.base.BaseViewHolder;
import com.newer.newerblogging.bean.microblog.RetweetedStatus;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.Utils;
import com.newer.newerblogging.view.HeadPicView;
import com.newer.newerblogging.view.PicGridView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Chalmers on 2016-09-13 12:36.
 * email:qxinhai@yeah.net
 */

/**
 * 微博适配器
 */
public class MicroAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SingleMicroblog> mMicroblogs;
    private String mAction;

    public MicroAdapter(Context context, ArrayList<SingleMicroblog> microblogs,
                        String action) {
        this.mContext = context;
        this.mMicroblogs = microblogs;
        this.mAction = action;
    }

    @Override
    public int getCount() {
        return mMicroblogs.size();
    }

    @Override
    public Object getItem(int position) {
        return mMicroblogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mMicroblogs.get(position).getRetweeted_status() == null ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderTypeOne viewHolderTypeOne;
        ViewHolderTypeTwo viewHolderTypeTwo;

        if (getItemViewType(position) == 0) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_micro_single, parent, false);
            viewHolderTypeOne = new ViewHolderTypeOne(convertView);

            viewHolderTypeOne.bindData(mMicroblogs.get(position));
        } else if (getItemViewType(position) == 1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_micro_repost, parent, false);
            viewHolderTypeTwo = new ViewHolderTypeTwo(convertView);
            viewHolderTypeTwo.bindData(mMicroblogs.get(position));
        }

        return convertView;
    }

    /**
     * 该ViewHolder布局用的是直接发送的微博
     */
    class ViewHolderTypeOne extends BaseViewHolder {

        @Bind(R.id.hpv_micro_header)
        HeadPicView hpvMicroHeader;
        @Bind(R.id.tv_micro_username)
        TextView tvMicroUsername;
        @Bind(R.id.tv_micro_time)
        TextView tvMicroTime;
        @Bind(R.id.iv_micro_more)
        ImageView ivMicroMore;
        @Bind(R.id.pgv_micro_pics)
        PicGridView pgvMicroPics;
        @Bind(R.id.tv_micro_content)
        TextView tvMicroContent;

        @Bind(R.id.iv_micro_like)
        ImageView ivMicroLike;
        @Bind(R.id.tv_micro_repeat)
        TextView tvMicroRepeat;
        @Bind(R.id.tv_micro_comment)
        TextView tvMicroComment;
        @Bind(R.id.tv_micro_praise)
        TextView tvMicroPraise;

        public ViewHolderTypeOne(View view) {
            super(view);
        }

        @Override
        public void bindData(Object obj) {
            bindCommonData((SingleMicroblog) obj);
            bindBottomData((SingleMicroblog) obj);
            bindListener((SingleMicroblog) obj);
            bottomBtnListener((SingleMicroblog) obj);
        }

        /**
         * 绑定的是微博主要的数据，包括头像，名称，内容之类的
         */
        public void bindCommonData(SingleMicroblog obj) {
            SingleMicroblog smb = obj;
            //根据url获得头像
            GlideForPicFromNet.netGetHeadWithUrl(mContext, smb.getUser().getProfile_image_url(), hpvMicroHeader.getLayoutParams().width,
                    hpvMicroHeader.getLayoutParams().height, new GlideForPicFromNet.HeadCallback() {
                        @Override
                        public void doCallbackData(Bitmap bitmap) {
                            hpvMicroHeader.setImageBitmap(bitmap);
                        }
                    });
            //用户名
            tvMicroUsername.setText(smb.getUser().getScreen_name());

            //时间
            tvMicroTime.setText(Utils.gmtToLocalTime(smb.getCreated_at()));

            //内容
            tvMicroContent.setText(smb.getText());

            //如果是有图片，则绑定图片
            GridViewAdapter gvAdapter = new GridViewAdapter(mContext, smb.getPic_urls());
            pgvMicroPics.setAdapter(gvAdapter);
        }

        /**
         * 绑定的是底部的包括收藏，评论，转发，点赞的数据
         */
        public void bindBottomData(SingleMicroblog obj) {
            //收藏
            if (obj.isFavorited()) {
                ivMicroLike.setImageResource(R.drawable.ic_like_ok);
            } else {
                ivMicroLike.setImageResource(R.drawable.ic_like_cancel);
            }

            //评论
            tvMicroComment.setText(String.valueOf(obj.getComments_count()));

            //转发
            tvMicroRepeat.setText(String.valueOf(obj.getReposts_count()));

            //点赞
            Drawable drawable = null;
            if (obj.isPraise()) {
                drawable = mContext.getResources()
                        .getDrawable(R.drawable.ic_praise_ok);
            } else {
                drawable = mContext.getResources()
                        .getDrawable(R.drawable.ic_praise_cancel);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvMicroPraise.setCompoundDrawables(drawable, null, null, null);
            tvMicroPraise.setText(String.valueOf(obj.getAttitudes_count()));
        }

        /**
         * 绑定控件的监听器
         */
        public void bindListener(final SingleMicroblog obj) {

            tvMicroContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeDataToDetailActivity(obj);
                }
            });
        }

        private void bottomBtnListener(final SingleMicroblog obj) {
            //收藏按钮的点击事件
            ivMicroLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //如果是把广播发送HomeFragment中去
                    if (mAction.equals(Config.ACTION_HOME_FRAGMENT)) {
                        intent.setAction(Config.ACTION_HOME_FRAGMENT_LIKE);
                    } else if (mAction.equals(Config.ACTION_FAVORITE_FRAGMENT)) {
                        intent.setAction(Config.ACTION_FAVORITE_FRAGMENT_LIKE);
                    }

                    //需要更改的位置
                    intent.putExtra(Config.ACTION_DATA_POSITION, mMicroblogs.indexOf(obj));
                    if (obj.isFavorited()) {  //如果该微博当前是收藏状态，那么则是取消收藏
                        intent.putExtra(Config.ACTION_TRUE_OR_FALSE, false);
                    } else {  //否则则是收藏微博
                        intent.putExtra(Config.ACTION_TRUE_OR_FALSE, true);
                    }
                    mContext.sendBroadcast(intent);
                }
            });

            //点赞的点击事件
            tvMicroPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //如果是把广播发送HomeFragment中去
                    if (mAction.equals(Config.ACTION_HOME_FRAGMENT)) {
                        intent.setAction(Config.ACTION_HOME_FRAGMENT_PRAISE);
                    }
                    //需要更改的位置
                    intent.putExtra(Config.ACTION_DATA_POSITION, mMicroblogs.indexOf(obj));
                    //确认或取消
                    if (obj.isPraise()) {
                        intent.putExtra(Config.ACTION_TRUE_OR_FALSE, false);
                    } else {
                        intent.putExtra(Config.ACTION_TRUE_OR_FALSE, true);
                    }
                    mContext.sendBroadcast(intent);
                }
            });

            //评论的点击事件
            tvMicroComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CreateCommentActivity.class);
                    intent.putExtra(Config.WEIBO_IDSTR, obj.getIdstr());
                    mContext.startActivity(intent);
                }
            });

            //转发的点击事件
            tvMicroRepeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RepostActivity.class);
                    String idStr = obj.getIdstr();
                    intent.putExtra(Config.WEIBO_IDSTR, idStr);
                    if (obj.getRetweeted_status() != null) {
                        intent.putExtra(Config.WEIBO_CONTENT,
                                "//@" + obj.getUser().getScreen_name() + ":" + obj.getText()
                        );
                    }
                    mContext.startActivity(intent);
                }
            });

            ivMicroMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //弹出菜单
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.inflate(R.menu.menu_btn_more);
                    popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // TODO 执行命令
                            if (item.getItemId() == R.id.action_more_hide_one) {
                                Toast.makeText(mContext, "屏蔽这条微博", Toast.LENGTH_SHORT).show();
                            } else if (item.getItemId() == R.id.action_more_hide_all) {
                                Toast.makeText(mContext, "屏蔽此人微博", Toast.LENGTH_SHORT).show();
                            } else if (item.getItemId() == R.id.action_more_delete) {
                                //如果操作的是他人微博，则不能删除微博
                                if (!obj.getUser().getIdstr().equals(AccessTokenKeeper.readAccessToken(mContext).getUid())) {
                                    Toast.makeText(mContext, "无法删除他人微博", Toast.LENGTH_SHORT).show();
                                } else {    //将要删除的微博位置通过广播发送到HomeFragment中去，然后执行删除命令
                                    Intent intent = new Intent();
                                    intent.setAction(Config.ACTION_HOME_FRAGMENT_DELETE);
                                    intent.putExtra(Config.ACTION_DATA_POSITION, mMicroblogs.indexOf(obj));
                                    mContext.sendBroadcast(intent);
                                }
                            }

                            return true;
                        }
                    });
                }
            });

            //头像的点击事件
            hpvMicroHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //放大高清头像
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra(Config.EXTRA_USER_ID, obj.getUser().getIdstr());
                    mContext.startActivity(intent);
                }
            });
        }

        public void takeDataToDetailActivity(SingleMicroblog obj) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Config.EXTRA_MICROBLOG, obj);
            intent.putExtra(Config.EXTRA_MICROBLOG_BUNDLE, bundle);

            if (obj.getRetweeted_status() == null) {
                intent.setClass(mContext, SingleWeiboDetailActivity.class);
            } else {
                intent.setClass(mContext, RepostWeiboDetailActivity.class);
            }

            mContext.startActivity(intent);
        }

    }

    /**
     * 因为布局与共同性，所以直接继承就可，然后改下其中某些数据
     * 该ViewHolder用的布局是转发的微博
     */
    class ViewHolderTypeTwo extends ViewHolderTypeOne {

        @Bind(R.id.tv_micro_repeat_content)
        TextView tvMicroRepeatContent;

        public ViewHolderTypeTwo(View view) {
            super(view);
        }

        @Override
        public void bindData(Object obj) {
            super.bindData(obj);
            SingleMicroblog smb = ((SingleMicroblog) obj);
            RetweetedStatus rs = smb.getRetweeted_status();
            //设置转发内容
            String text = "@" + ((SingleMicroblog) obj).getRetweeted_status().getUser()
                    .getScreen_name() + ": " + ((SingleMicroblog) obj).getRetweeted_status()
                    .getText();
            tvMicroContent.setText(text);
            //转发后，自己的评论内容
            tvMicroRepeatContent.setText(smb.getText());

            //重新设置图片，该图片显示的是转发的微博中的内容
            GridViewAdapter gvAdapter = new GridViewAdapter(mContext, rs.getPic_urls());
            pgvMicroPics.setAdapter(gvAdapter);

            bindListener((SingleMicroblog) obj);
        }

        /**
         * 点击事件
         *
         * @param obj 微博对象
         */
        @Override
        public void bindListener(final SingleMicroblog obj) {
            //原微博的内容
            tvMicroContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeDataToDetailActivity(obj);
                }
            });

            //转发微博的内容
            tvMicroRepeatContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeDataToDetailActivity(obj);
                }
            });
        }
    }
}
