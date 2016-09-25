package com.newer.newerblogging.activity;

import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.UserHomeFragmentAdapter;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.UserCounts;
import com.newer.newerblogging.bean.UserInfo;
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
