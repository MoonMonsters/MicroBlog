package com.newer.newerblogging.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newer.newerblogging.R;
import com.newer.newerblogging.bean.comment.Comment;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.Utils;
import com.newer.newerblogging.view.HeadPicView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chalmers on 2016-09-19 09:11.
 * email:qxinhai@yeah.net
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Context mContext;
    private ArrayList<Comment> mComments;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.mContext = context;
        this.mComments = comments;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_micro_comment,parent,false);
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

    class CommentsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.hpv_micro_comment_header)
        HeadPicView hpvMicroCommentHeader;
        @Bind(R.id.tv_micro_comment_name)
        TextView tvMicroCommentName;
        @Bind(R.id.tv_micro_comment_content)
        TextView tvMicroCommentContent;
        @Bind(R.id.tv_micro_comment_time)
        TextView tvMicroCommentTime;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        /**
         * 绑定数据
         * @param obj 需要绑定的对象
         */
        public void bindData(Object obj) {

            Comment comment = (Comment) obj;

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
        }
    }
}
