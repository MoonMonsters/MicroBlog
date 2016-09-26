package com.newer.newerblogging.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.CommentsAdapter;
import com.newer.newerblogging.adapter.GridViewAdapter;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.bean.comment.AllComments;
import com.newer.newerblogging.bean.comment.Comment;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.bean.microblog.User;
import com.newer.newerblogging.utils.BlogInterfaceConfig;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.LoggerUtil;
import com.newer.newerblogging.utils.NetConnectionUtil;
import com.newer.newerblogging.utils.Utils;
import com.newer.newerblogging.view.HeadPicView;
import com.newer.newerblogging.view.PicGridView;

import java.util.ArrayList;

import butterknife.Bind;

public class RepostWeiboDetailActivity extends BaseActivity {

    /**
     * 头像
     */
    @Bind(R.id.hpv_micro_header)
    HeadPicView hpvMicroHeader;
    /**
     * 用户名
     */
    @Bind(R.id.tv_micro_username)
    TextView tvMicroUsername;
    /**
     * 时间
     */
    @Bind(R.id.tv_micro_time)
    TextView tvMicroTime;
    /**
     * 隐藏
     */
    @Bind(R.id.iv_micro_more)
    ImageView ivMicroMore;
    /**
     * 转发的评论内容
     */
    @Bind(R.id.tv_micro_repeat_content)
    TextView tvMicroRepeatContent;
    /**
     * 原微博内容
     */
    @Bind(R.id.tv_micro_content)
    TextView tvMicroContent;
    /**
     * 图片
     */
    @Bind(R.id.pgv_micro_pics)
    PicGridView pgvMicroPics;
    /**
     * 隐藏
     */
    @Bind(R.id.layout_micro_bottom)
    RelativeLayout layoutMicroBottom;
    @Bind(R.id.rv_detail_content)
    public RecyclerView rvDetailContent;

    private SingleMicroblog mSingleMicroblog;
    ArrayList<Comment> mComments;
    CommentsAdapter mCommentAdapter;

    String id_command = BlogInterfaceConfig.MAX_MICRO_NUM;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_repost_weibo_detail;
    }

    @Override
    public void initListener() {
        rvDetailContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == layoutManager.getItemCount() - 1) {
                        refresh();
                    }
                }
            }
        });

        hpvMicroHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepostWeiboDetailActivity.this, UserHomeActivity.class);
                intent.putExtra(Config.EXTRA_USER_ID, mSingleMicroblog.getUser().getIdstr());
                RepostWeiboDetailActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        ivMicroMore.setVisibility(View.GONE);
        layoutMicroBottom.setVisibility(View.GONE);

        mSingleMicroblog = getIntent().getBundleExtra(Config.EXTRA_MICROBLOG_BUNDLE)
                .getParcelable(Config.EXTRA_MICROBLOG);

        User user = mSingleMicroblog.getUser();
        GlideForPicFromNet.netGetHeadWithUrl(this, user.getProfile_image_url(), 50, 50, new GlideForPicFromNet.HeadCallback() {
            @Override
            public void doCallbackData(Bitmap bitmap) {
                hpvMicroHeader.setImageBitmap(bitmap);
            }
        });
        tvMicroUsername.setText(user.getScreen_name());
        tvMicroTime.setText(Utils.gmtToLocalTime(mSingleMicroblog.getCreated_at()));

        //转发的评论
        tvMicroRepeatContent.setText(mSingleMicroblog.getText());
        //原文评论
        tvMicroContent.setText("@" + mSingleMicroblog.getRetweeted_status().getUser().getScreen_name() +
                ":" + mSingleMicroblog.getRetweeted_status().getText());

        GridViewAdapter gvAdapter = new GridViewAdapter(this, mSingleMicroblog.getRetweeted_status().getPic_urls());
        pgvMicroPics.setAdapter(gvAdapter);

        rvDetailContent.setLayoutManager(new GridLayoutManager(this, 1));
        mComments = new ArrayList<>();
        mCommentAdapter = new CommentsAdapter(this, mComments);
        rvDetailContent.setAdapter(mCommentAdapter);


        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        id_command = BlogInterfaceConfig.MAX_MICRO_NUM;
    }

    private void refresh() {
        NetConnectionUtil.netToShowComments(this, mSingleMicroblog.getIdstr(),
                id_command, 20, 1, 0, new NetConnectionUtil.NetCallback() {
                    @Override
                    public void doSuccess(String data) {
//                        Collections.reverse(list);
                        ArrayList<Comment> list = new Gson().fromJson(data, AllComments.class).getComments();
                        if (list != null && list.size() != 0) {

                            mComments.addAll(mComments.size() == 0 ? 0 : mComments.size() - 1,
                                    list);
                            LoggerUtil.i("COMMENT", mComments.toString());
                            id_command = String.valueOf(Long.valueOf(list.get(list.size() - 1).getIdstr()) - 1);
                            mCommentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void doFail(String message) {

                    }
                });
    }
}
