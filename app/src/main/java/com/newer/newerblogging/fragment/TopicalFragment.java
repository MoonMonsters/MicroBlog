package com.newer.newerblogging.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.TopicalFragmentAdapter;
import com.newer.newerblogging.base.BaseFragment;

import butterknife.Bind;

/**
 * 热门
 */
public class TopicalFragment extends BaseFragment {

    @Bind(R.id.tl_topical_tab)
    TabLayout tlTopicalTab;
    @Bind(R.id.vp_topical_content)
    ViewPager vpTopicalContent;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_topical;
    }

    @Override
    public void initData() {
        vpTopicalContent.setAdapter(new TopicalFragmentAdapter(getActivity().getSupportFragmentManager()));
        tlTopicalTab.setupWithViewPager(vpTopicalContent);
    }
}
