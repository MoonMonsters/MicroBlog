package com.newer.newerblogging.fragment;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseFragment;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 抽屉中微博项Fragment
 */
public class WeiboFragment extends BaseFragment {
    /**
     * 底部导航
     */
    @Bind(R.id.bnb_weibo_tab)
    BottomNavigationBar bnbWeiboTab;

    /**
     * HomeFragment 的位置
     */
    public static final int POSITION_HOME_FRAGMENT = 0;
    /**
     * ContactsFragment的位置
     */
    public static final int POSITION_CONTACTS_FRAGMENT = 1;
    /**
     * TopicalFragment的位置
     */
    public static final int POSITION_TOPICAL_FRAGMENT = 2;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_weibo;
    }

    private HomeFragment mHomeFragment;
    private ContactsFragment mContactsFragment;
    private TopicalFragment mTopicalFragment;

    @Override
    public void initData() {

        /** 设置模式 */
        bnbWeiboTab.setMode(BottomNavigationBar.MODE_DEFAULT);
        /** 添加导航项 */
        bnbWeiboTab.addItem(new BottomNavigationItem(R.drawable.ic_home, "首页"))
                .setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.ic_friends, "联系人"))
                .setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.ic_hot, "热门"))
                .setActiveColor(R.color.colorPrimary)
                .setFirstSelectedPosition(0)
                .initialise();  //重新绘制

        mHomeFragment = (HomeFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_HOME);
        mContactsFragment = (ContactsFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_CONTACTS);
        mTopicalFragment = (TopicalFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_TOPICAL);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_weibo_content, mHomeFragment)
                .add(R.id.frame_weibo_content, mContactsFragment)
                .add(R.id.frame_weibo_content, mTopicalFragment)
                .commit();

        //设置默认项
        showHomeFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }

    /**
     * 设置监听器方法
     */
    private void initListener() {
        bnbWeiboTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == POSITION_HOME_FRAGMENT) {
                    showHomeFragment();
                } else if (position == POSITION_CONTACTS_FRAGMENT) {
                    showContactsFragment();
                } else if (position == POSITION_TOPICAL_FRAGMENT) {
                    showTopicalFragment();
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 显示HomeFragment
     */
    private void showHomeFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(mHomeFragment)
                .hide(mContactsFragment)
                .hide(mTopicalFragment)
                .commit();
    }

    /**
     * 显示TopicalFragment
     */
    private void showTopicalFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(mTopicalFragment)
                .hide(mContactsFragment)
                .hide(mHomeFragment)
                .commit();
    }

    /**
     * 显示ContactsFragment
     */
    private void showContactsFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(mContactsFragment)
                .hide(mHomeFragment)
                .hide(mTopicalFragment)
                .commit();
    }
}
