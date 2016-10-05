package com.newer.newerblogging.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.UserHomeActivity;
import com.newer.newerblogging.bean.comment.Comment;
import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.NetConnectionUtil;
import com.newer.newerblogging.utils.Utils;
import com.newer.newerblogging.view.HeadPicView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chalmers on 2016-09-19 09:11.
 * email:qxinhai@yeah.net
 */

/**
 * 评论Adapter
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Context mContext;
    private ArrayList<Comment> mComments;

    private String mWeiboIdStr;

    public CommentsAdapter(Context context, ArrayList<Comment> comments,
                           String weiboIdStr) {
        this.mContext = context;
        this.mComments = comments;
        this.mWeiboIdStr = weiboIdStr;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_micro_comment, parent, false);
        CommentsViewHolder viewHolder = new CommentsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.bindData(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.hpv_micro_comment_header)
        HeadPicView hpvMicroCommentHeader;
        @Bind(R.id.tv_micro_comment_name)
        TextView tvMicroCommentName;
        @Bind(R.id.tv_micro_comment_content)
        TextView tvMicroCommentContent;
        @Bind(R.id.tv_micro_comment_time)
        TextView tvMicroCommentTime;

        private Comment mComment;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * 绑定数据
         *
         * @param obj 需要绑定的对象
         */
        public void bindData(Object obj) {

            Comment comment = (Comment) obj;
            this.mComment = comment;
            GlideForPicFromNet.netGetHeadWithUrl(mContext,
                    ((Comment) obj).getUser().getProfile_image_url(),
                    40, 40, new GlideForPicFromNet.HeadCallback() {
                        @Override
                        public void doCallbackData(Bitmap bitmap) {
                            hpvMicroCommentHeader.setImageBitmap(bitmap);
                        }
                    });
            tvMicroCommentContent.setText(comment.getText());
            tvMicroCommentName.setText(comment.getUser().getScreen_name());
            tvMicroCommentTime.setText(Utils.gmtToLocalTime(comment.getCreated_at()));

            initListener((Comment) obj);
        }

        private void initListener(final Comment comment) {
            hpvMicroCommentHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra(Config.EXTRA_USER_ID, comment.getUser().getIdstr());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            final PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.inflate(R.menu.menu_comment);
            popupMenu.setGravity(Gravity.CENTER);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    //回复菜单
                    if (item.getItemId() == R.id.action_comment_reply) {

                        //弹出对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View dialogView = LayoutInflater.from(mContext)
                                .inflate(R.layout.item_comment_reply_dialog, null);
                        builder.setView(dialogView);
                        final AlertDialog dialog = builder.create();
                        //设置标题
                        dialog.setTitle("回复: " + mComment.getUser().getScreen_name());
                        dialog.show();

                        //对话框中的控件
                        final EditText etCommentReply = (EditText) dialogView.findViewById(R.id.et_comment_reply);
                        Button btnCommentCancel = (Button) dialogView.findViewById(R.id.btn_comment_cancel);
                        Button btnCommentReply = (Button) dialogView.findViewById(R.id.btn_comment_reply);

                        //取消
                        btnCommentCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        //回复
                        btnCommentReply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //得到输入框中的内容
                                String text = etCommentReply.getText().toString();
                                NetConnectionUtil.netToReplyComments(mContext, mComment.getIdstr(),
                                        mWeiboIdStr, text, new NetConnectionUtil.NetCallback() {
                                            @Override
                                            public void doSuccess(String data) {
                                                Comment comment = new Gson().fromJson(data, Comment.class);
                                                //添加到第一个位置
                                                mComments.add(0, comment);
                                                //刷新
                                                CommentsAdapter.this.notifyDataSetChanged();
                                                Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                                                //隐藏对话框
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void doFail(String message) {
                                                Toast.makeText(mContext, "回复失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

                        //删除
                    } else if (item.getItemId() == R.id.action_comment_delete) {
                        if (mComment.getUser().getIdstr().equals(AccessTokenKeeper.readAccessToken(mContext)
                                .getUid())) {
                            NetConnectionUtil.netToDestroyComments(mContext, mComment.getIdstr(), new NetConnectionUtil.NetCallback() {
                                @Override
                                public void doSuccess(String data) {
                                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                    //从界面中该删除的评论移除掉，并且刷新界面
                                    mComments.remove(mComment);
                                    CommentsAdapter.this.notifyDataSetChanged();
                                }

                                @Override
                                public void doFail(String message) {
                                    Toast.makeText(mContext, "删除失败，请稍后再试.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {    //如果删除的评论不是本人的，那么弹出提示框
                            Toast.makeText(mContext, "不能删除他人评论", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //隐藏menu
                    popupMenu.dismiss();
                    return true;
                }
            });
        }
    }
}
