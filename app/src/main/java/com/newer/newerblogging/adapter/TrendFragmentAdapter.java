package com.newer.newerblogging.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newer.newerblogging.fragment.FragmentFactory;


/**
 * Created by Chalmers on 2016-09-22 19:46.
 * email:qxinhai@yeah.net
 */
public class TrendFragmentAdapter extends FragmentPagerAdapter {

    String[] mTitles = new String[]{"小时话题", "当日话题", "本周话题"};
    String[] mTypes = new String[]{FragmentFactory.FRAGMENT_TRENDS_HOURLY,
            FragmentFactory.FRAGMENT_TRENDS_DAILY,
            FragmentFactory.FRAGMENT_TRENDS_WEEKLY};

    public TrendFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getInstance(mTypes[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
