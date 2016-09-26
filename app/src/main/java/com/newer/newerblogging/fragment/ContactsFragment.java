package com.newer.newerblogging.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.ContactFragmentAdapter;
import com.newer.newerblogging.base.BaseFragment;

import butterknife.Bind;

/**
 * 联系人
 */
public class ContactsFragment extends BaseFragment {


    @Bind(R.id.tl_contacts_tab)
    TabLayout tlContactsTab;
    @Bind(R.id.vP_contacts_content)
    ViewPager vPContactsContent;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initData() {
        vPContactsContent.setAdapter(new ContactFragmentAdapter(getActivity().getSupportFragmentManager()));
        vPContactsContent.setOffscreenPageLimit(3);
        tlContactsTab.setupWithViewPager(vPContactsContent);
    }
}