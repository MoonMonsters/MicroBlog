package com.newer.newerblogging.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newer.newerblogging.application.NewerApplication;
import com.newer.newerblogging.fragment.FragmentFactory;
import com.newer.newerblogging.utils.SharedPrefUtil;

/**
 * Created by Chalmers on 2016-09-26 10:05.
 * email:qxinhai@yeah.net
 */

/**
 * 联系人界面的三个Fragment适配器
 */
public class ContactFragmentAdapter extends FragmentPagerAdapter {


    private static final String[] mTitles = new String[]{"关注" + SharedPrefUtil.getFollowingCount(NewerApplication.getInstance()),
            "粉丝" + SharedPrefUtil.getFollowersCount(NewerApplication.getInstance()),
            "互粉" + SharedPrefUtil.getBiFollowersCount(NewerApplication.getInstance())};

    private static final String[] mTypes = new String[]{
            FragmentFactory.FRAGMENT_CONTACT_FRIENDSHIP_FRIENDS,
            FragmentFactory.FRAGMENT_CONTACT_FRIENDSHIP_FOLLOWERS,
            FragmentFactory.FRAGMENT_CONTACT_FRIENDSHIP_BILATERAL
    };

    public ContactFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getInstance(mTypes[position]);
    }

    @Override
    public int getCount() {
        return mTypes.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
