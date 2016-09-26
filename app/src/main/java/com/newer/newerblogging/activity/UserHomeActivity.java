package com.newer.newerblogging.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.UserHomeFragmentAdapter;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.UserCounts;
import com.newer.newerblogging.bean.UserInfo;
import com.newer.newerblogging.bean.microblog.MicroblogPic;
import com.newer.newerblogging.fragment.UserHomeAboutFragment;
import com.newer.newerblogging.fragment.UserHomeWeiboFragment;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.NetConnectionUtil;
import com.newer.newerblogging.view.HeadPicView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 用户主页
 */
public class UserHomeActivity extends BaseActivity {

    @Bind(R.id.iv_user_home_bg)
    ImageView ivUserHomeBg;
    @Bind(R.id.tv_user_home_name)
    TextView tvUserHomeName;
    @Bind(R.id.tv_user_home_count)
    TextView tvUserHomeCount;
    @Bind(R.id.hpv_user_home_header)
    HeadPicView hpvUserHomeHeader;
    @Bind(R.id.tl_user_home_tab)
    TabLayout tlUserHomeTab;
    @Bind(R.id.vp_user_home_content)
    ViewPager vpUserHomeContent;
    @Bind(R.id.iv_user_home_attention)
    ImageView ivUserHomeAttention;

    private ArrayList<String> mTitles;
    private ArrayList<BaseFragment> mFragments;
    private UserHomeFragmentAdapter mAdapter;

    /**
     * 传递过来的用户id
     */
    private String mUserId;
    /**
     * 用户信息
     */
    private UserInfo mUserInfo;
    /**
     * 用户的粉丝数，关注数，微博数，在用户信息中没有提供
     */
    private UserCounts mUserCounts;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_user_home;
    }

    @Override
    public void initListener() {
        ivUserHomeAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfo.isFollowing()) {  //如果是关注状态，点击后取消关注
                    NetConnectionUtil.netToCreateFriendShips(UserHomeActivity.this,
                            mUserInfo.getIdstr(), new NetConnectionUtil.NetCallback() {
                                @Override
                                public void doSuccess(String data) {
                                    Toast.makeText(UserHomeActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                                    ivUserHomeAttention.setImageResource(R.drawable.ic_attention_ok);
                                }

                                @Override
                                public void doFail(String message) {
                                    Toast.makeText(UserHomeActivity.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {    //取关变成关注
                    NetConnectionUtil.netToDestroyFriendShips(UserHomeActivity.this,
                            mUserInfo.getIdstr(), new NetConnectionUtil.NetCallback() {
                                @Override
                                public void doSuccess(String data) {
                                    Toast.makeText(UserHomeActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                    ivUserHomeAttention.setImageResource(R.drawable.ic_attention_cancel);
                                }

                                @Override
                                public void doFail(String message) {
                                    Toast.makeText(UserHomeActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        //头像的点击事件，点击后可查看高清头像
        hpvUserHomeHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, ImgDetailActivity.class);
                ArrayList<MicroblogPic> microblogPics = new ArrayList<>();
                MicroblogPic pic = new MicroblogPic(mUserInfo.getAvatar_hd());
                microblogPics.add(pic);
                //把图片的url传递到另一个Activity中去
                intent.putExtra(Config.IMAGE_LIST, microblogPics);
                intent.putExtra(Config.IMAGE_POSITION, 0);
                UserHomeActivity.this.startActivity(intent);
            }
        });

        //背景图放大
        ivUserHomeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfo.getCover_image_phone() != null) {
                    Intent intent = new Intent(UserHomeActivity.this, ImgDetailActivity.class);
                    ArrayList<MicroblogPic> microblogPics = new ArrayList<MicroblogPic>();
                    MicroblogPic pic = new MicroblogPic(mUserInfo.getCover_image_phone());
                    microblogPics.add(pic);
                    //把图片的url传递到另一个Activity中去
                    intent.putExtra(Config.IMAGE_LIST, microblogPics);
                    intent.putExtra(Config.IMAGE_POSITION, 0);
                    UserHomeActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(UserHomeActivity.this, "没有背景图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initData() {
        //得到传递过来的用户id
        mUserId = getIntent().getStringExtra(Config.EXTRA_USER_ID);

        //标题
        mTitles = new ArrayList<>();
        //Fragment
        mFragments = new ArrayList<>();
        mAdapter = new UserHomeFragmentAdapter(getSupportFragmentManager(),
                mTitles, mFragments);
        vpUserHomeContent.setAdapter(mAdapter);
        tlUserHomeTab.setupWithViewPager(vpUserHomeContent);

        //得到用户数据
        NetConnectionUtil.netToShowUser(this, mUserId, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {
                //得到用户数据
                mUserInfo = new Gson().fromJson(data, UserInfo.class);

                tvUserHomeName.setText(mUserInfo.getScreen_name());
                GlideForPicFromNet.netGetHeadWithUrl(UserHomeActivity.this, mUserInfo.getProfile_image_url(),
                        hpvUserHomeHeader.getLayoutParams().width,
                        hpvUserHomeHeader.getLayoutParams().height,
                        new GlideForPicFromNet.HeadCallback() {
                            @Override
                            public void doCallbackData(Bitmap bitmap) {
                                hpvUserHomeHeader.setImageBitmap(bitmap);
                            }
                        });
                GlideForPicFromNet.netGetHeadWithUrl(UserHomeActivity.this,
                        mUserInfo.getCover_image_phone(), ivUserHomeBg.getLayoutParams().width,
                        ivUserHomeBg.getLayoutParams().height,
                        new GlideForPicFromNet.HeadCallback() {
                            @Override
                            public void doCallbackData(Bitmap bitmap) {
                                ivUserHomeBg.setImageBitmap(bitmap);
                            }
                        });

                //如果是关注的，那么则显示为  未关注的图标
                if (mUserInfo.isFollowing()) {
                    ivUserHomeAttention.setImageResource(R.drawable.ic_attention_cancel);
                } else {    //跟上面相反
                    ivUserHomeAttention.setImageResource(R.drawable.ic_attention_ok);
                }

            }

            @Override
            public void doFail(String message) {

            }
        });

        //得到粉丝相关数据
        NetConnectionUtil.netToUsersCounts(this, mUserId, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {

                //得到粉丝数据
                mUserCounts = new UserCounts();
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    mUserCounts.setId(jsonObject.getLong("id"));
                    mUserCounts.setFollowers_count(jsonObject.getString("followers_count"));
                    mUserCounts.setFriends_count(jsonObject.getString("friends_count"));
                    mUserCounts.setStatuses_count(jsonObject.getString("statuses_count"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String text = String.format("关注:%s | 粉丝:%s",
                        String.valueOf(mUserCounts.getFriends_count()),
                        String.valueOf(mUserCounts.getFollowers_count()));
                tvUserHomeCount.setText(text);

                mTitles.add("关于");
                mTitles.add("微博" + mUserCounts.getStatuses_count());

                mFragments.add(new UserHomeAboutFragment().getInstance(mUserInfo));
                mFragments.add(new UserHomeWeiboFragment().getInstance(mUserId));

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void doFail(String message) {
                String text = String.format("关注:%s | 粉丝:%s",
                        "0",
                        "0");
                tvUserHomeCount.setText(text);
            }
        });
    }
}
