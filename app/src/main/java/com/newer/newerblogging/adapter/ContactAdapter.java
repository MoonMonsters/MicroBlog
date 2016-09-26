package com.newer.newerblogging.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.UserHomeActivity;
import com.newer.newerblogging.base.BaseViewHolder;
import com.newer.newerblogging.bean.UserInfo;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.view.HeadPicView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Chalmers on 2016-09-26 10:41.
 * email:qxinhai@yeah.net
 */

/**
 * 联系人界面，好友栏适配器
 */
public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UserInfo> mUsers;

    public ContactAdapter(Context context, ArrayList<UserInfo> users) {
        this.mContext = context;
        this.mUsers = users;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(mUsers.get(position));

        return convertView;
    }

    class ViewHolder extends BaseViewHolder {

        @Bind(R.id.hpv_contact_header)
        HeadPicView hpvContactHeader;
        @Bind(R.id.tv_contact_name)
        TextView tvContactName;
        @Bind(R.id.tv_contact_description)
        TextView tvContactDescription;
        @Bind(R.id.iv_contact_attention)
        ImageView ivContactAttention;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindData(Object obj) {
            UserInfo user = (UserInfo) obj;

            GlideForPicFromNet.netGetHeadWithUrl(mContext, user.getProfile_image_url(),
                    hpvContactHeader.getLayoutParams().width, hpvContactHeader.getLayoutParams().height,
                    new GlideForPicFromNet.HeadCallback() {
                        @Override
                        public void doCallbackData(Bitmap bitmap) {
                            hpvContactHeader.setImageBitmap(bitmap);
                        }
                    });
            tvContactName.setText(user.getScreen_name());
            try {
                if (!TextUtils.isEmpty(user.getDescription())) {
                    tvContactDescription.setText(user.getDescription());
                } else {
                    tvContactDescription.setText("该用户很懒，没有设置签名");
                }
            } catch (Exception e) {
                tvContactDescription.setText("该用户很懒，没有设置签名");
            }

            if (user.isFollowing()) {
                ivContactAttention.setImageResource(R.drawable.ic_contact_attention_cancel);
            } else {
                ivContactAttention.setImageResource(R.drawable.ic_contact_attention_ok);
            }

            ClickListener clickListener = new ClickListener(user);
            ivContactAttention.setOnClickListener(clickListener);
            hpvContactHeader.setOnClickListener(clickListener);
            tvContactDescription.setOnClickListener(clickListener);
            tvContactName.setOnClickListener(clickListener);
        }

        class ClickListener implements View.OnClickListener {

            private UserInfo mUserInfo;

            public ClickListener(UserInfo userInfo) {
                this.mUserInfo = userInfo;
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.iv_contact_attention) {
                    Toast.makeText(mContext, "发送私信", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra(Config.EXTRA_USER_ID, mUserInfo.getIdstr());
                    mContext.startActivity(intent);
                }
            }
        }
    }
}
