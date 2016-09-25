package com.newer.newerblogging.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newer.newerblogging.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-24 16:56.
 * email:qxinhai@yeah.net
 */

public class UserHomeFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mTitles;
    private ArrayList<BaseFragment> mFragments;

    public UserHomeFragmentAdapter(FragmentManager fm,
                                   ArrayList<String> titles,
                                   ArrayList<BaseFragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
