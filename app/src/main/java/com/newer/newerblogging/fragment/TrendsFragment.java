package com.newer.newerblogging.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.TrendFragmentAdapter;
import com.newer.newerblogging.base.BaseFragment;

import butterknife.Bind;

public class TrendsFragment extends BaseFragment {

    @Bind(R.id.tl_trend_tab)
    TabLayout tlTrendTab;
    @Bind(R.id.vp_trend_content)
    ViewPager vpTrendContent;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_trends;
    }

    @Override
    public void initData() {

        vpTrendContent.setAdapter(new TrendFragmentAdapter(getActivity().getSupportFragmentManager()));
        tlTrendTab.setupWithViewPager(vpTrendContent);

    }
}
