package com.newer.newerblogging.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.UserInfo;
import com.newer.newerblogging.utils.Utils;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeAboutFragment extends BaseFragment {

    private final static String EXTRA_USERINFO = "extra_userinfo";
    @Bind(R.id.et_about_description)
    EditText etAboutDescription;
    @Bind(R.id.et_about_address)
    EditText etAboutAddress;
    @Bind(R.id.et_about_blog_url)
    EditText etAboutBlogUrl;
    @Bind(R.id.et_about_sex)
    EditText etAboutSex;
    @Bind(R.id.et_about_created_time)
    EditText etAboutCreatedTime;
    @Bind(R.id.et_about_verified_reason)
    EditText etAboutVerifiedReason;
    @Bind(R.id.et_about_tags)
    EditText etAboutTags;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_user_home_about;
    }

    @Override
    public void initData() {
        UserInfo userInfo = getArguments().getParcelable(EXTRA_USERINFO);

        //个人描述
        try {
            etAboutDescription.setText(userInfo.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //位置
        try {
            etAboutAddress.setText(userInfo.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //博客地址
        try {
            etAboutBlogUrl.setText(userInfo.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //性别
        try {
            String sex = null;
            switch (userInfo.getGender()) {
                case "m":
                    sex = "男";
                    break;
                case "f":
                    sex = "女";
                    break;
                case "n":
                    sex = "保密";
                    break;
            }
            etAboutSex.setText(sex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建时间
        try {
            etAboutCreatedTime.setText(Utils.gmtToLocalTime(userInfo.getCreated_at()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //认证理由
        try {
            etAboutVerifiedReason.setText(userInfo.getVerified_reason());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //身份说明
        try {
            etAboutTags.setText(userInfo.getAbility_tags());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseFragment getInstance(UserInfo userInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USERINFO, userInfo);
        setArguments(bundle);

        return this;
    }
}
