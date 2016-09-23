package com.newer.newerblogging.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newer.newerblogging.fragment.FragmentFactory;

/**
 * Created by Chalmers on 2016-09-22 15:59.
 * email:qxinhai@yeah.net
 */
public class TopicalFragmentAdapter extends FragmentPagerAdapter {

    /** 标题 */
    private String[] mTitles = new String[]{"热门微博","热门话题"};
    /** Fragment类型 */
    private String[] mTypes = new String[]{FragmentFactory.FRAGMENT_PUBLIC_STATUES,
    FragmentFactory.FRAGMENT_TRENDS};

    public TopicalFragmentAdapter(FragmentManager fm) {
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
