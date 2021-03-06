package com.newer.newerblogging.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.fragment.FavoriteFragment;
import com.newer.newerblogging.fragment.FragmentFactory;
import com.newer.newerblogging.fragment.SettingFragment;
import com.newer.newerblogging.fragment.WeiboFragment;
import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.GlideForPicFromNet;
import com.newer.newerblogging.utils.SharedPrefUtil;
import com.newer.newerblogging.view.HeadPicView;
import com.newer.newerblogging.view.MarqueeText;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tl_main_bar)
    Toolbar tlMainBar;
    @Bind(R.id.nv_main_navigation)
    NavigationView nvMainNavigation;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dlMainDrawer;
    @Bind(R.id.hpv_main_header)
    HeadPicView hpvMainHeader;
    @Bind(R.id.tv_main_name)
    TextView tvMainName;

    private FavoriteFragment mFavoriteFragment;
    private WeiboFragment mWeiboFragment;
    private SettingFragment mSettingFragment;

    private BaseFragment mCurrentFragment;

    /**
     * 抽屉
     */
    private ActionBarDrawerToggle abdtDrawerToggle;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {
        dlMainDrawer.addDrawerListener(abdtDrawerToggle);
        nvMainNavigation.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        hpvMainHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMainDrawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void initData() {
        //设置ToolBar
        setSupportActionBar(tlMainBar);
        //设置标题为空
        setTitle(null);
        //设置顶部栏的数据，包括用户名和头像
        setTopData();

        abdtDrawerToggle = new ActionBarDrawerToggle(this, dlMainDrawer, R.string.app_name, R.string.app_name) {
            /*
            当抽屉打开时，会调用该方法，此时可以获得布局
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                setNavigationHead(drawerView);
            }
        };
        abdtDrawerToggle.syncState();

        mWeiboFragment = (WeiboFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_WEIBO);
        mFavoriteFragment = (FavoriteFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_FAVORITE);
        mSettingFragment = (SettingFragment) FragmentFactory.getInstance(FragmentFactory.FRAGMENT_SETTING);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_main_content, mWeiboFragment)
                .add(R.id.frame_main_content, mFavoriteFragment)
                .add(R.id.frame_main_content, mSettingFragment)
                .hide(mWeiboFragment)
                .hide(mFavoriteFragment)
                .hide(mSettingFragment)
                .commit();
        showWeiboFragment();
    }

    /**
     * NavigationView的监听器，点击抽屉用
     */
    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.action_nav_favorite) {  //收藏
                showFavoriteFragment();
            } else if (item.getItemId() == R.id.action_nav_blog) {  //微博
                showWeiboFragment();
            } else if (item.getItemId() == R.id.action_nav_setting) {   //设置
                showSettingFragment();
            }

            //关闭抽屉
            dlMainDrawer.closeDrawers();
            return true;
        }
    };

    /**
     * 显示SettingFragment
     */
    private void showSettingFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mSettingFragment)
                .hide(mFavoriteFragment)
                .hide(mWeiboFragment)
                .commit();
        mCurrentFragment = mSettingFragment;
    }

    /**
     * 显示WeiboFragment
     */
    private void showWeiboFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mWeiboFragment)
                .hide(mFavoriteFragment)
                .hide(mSettingFragment)
                .commit();
        mCurrentFragment = mWeiboFragment;
    }

    /**
     * 显示FavoriteFragment
     */
    private void showFavoriteFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mFavoriteFragment)
                .hide(mWeiboFragment)
                .hide(mSettingFragment)
                .commit();
        mCurrentFragment = mFavoriteFragment;
    }

    /**
     * 设置顶部栏数据包括头像和用户名
     */
    private void setTopData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置头像
                GlideForPicFromNet.netGetHeadWithUrl(MainActivity.this, SharedPrefUtil.getUserHeadImgMiddle(MainActivity.this),
                        hpvMainHeader.getLayoutParams().width, hpvMainHeader.getLayoutParams().height,
                        new GlideForPicFromNet.HeadCallback() {
                            @Override
                            public void doCallbackData(Bitmap bitmap) {
                                hpvMainHeader.setImageBitmap(bitmap);
                            }
                        });
                //设置用户名
                tvMainName.setText(SharedPrefUtil.getUsername(MainActivity.this));
            }
        }, 1000);
    }

    /**
     * 当打开抽屉时，显示上面的头像
     *
     * @param drawerView 抽屉布局
     */
    private void setNavigationHead(View drawerView) {

        //设置头像
        final HeadPicView hpvNavHeader = (HeadPicView) drawerView.findViewById(R.id.hpv_nav_header);
        GlideForPicFromNet.netGetHeadWithUrl(MainActivity.this, SharedPrefUtil.getUserHeadImgLarge(this),
                hpvNavHeader.getLayoutParams().width, hpvNavHeader.getLayoutParams().height,
                new GlideForPicFromNet.HeadCallback() {
                    @Override
                    public void doCallbackData(Bitmap bitmap) {
                        hpvNavHeader.setImageBitmap(bitmap);
                    }
                });
        //设置用户名
        TextView tvNavUsername = (TextView) drawerView.findViewById(R.id.tv_nav_username);
        tvNavUsername.setText(SharedPrefUtil.getUsername(this));
        //设置简介
        MarqueeText mtNavDescription = (MarqueeText) drawerView.findViewById(R.id.mt_nav_description);
        mtNavDescription.setText(SharedPrefUtil.getDescription(this));
        //设置背景图
        final RelativeLayout layout_nav_bg = (RelativeLayout) drawerView.findViewById(R.id.layout_nav_bg);
        GlideForPicFromNet.netGetHeadWithUrl(this, SharedPrefUtil.getCoverBgImg(this),
                layout_nav_bg.getLayoutParams().width, layout_nav_bg.getLayoutParams().height,
                new GlideForPicFromNet.HeadCallback() {
                    @Override
                    public void doCallbackData(Bitmap bitmap) {
                        layout_nav_bg.setBackground(new BitmapDrawable(MainActivity.this.getResources(), bitmap));
                    }
                });
        hpvNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                intent.putExtra(Config.EXTRA_USER_ID, AccessTokenKeeper.readAccessToken(MainActivity.this).getUid());
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * 重写回退方法
     */
    @Override
    public void onBackPressed() {
        //如果当前不是WeiboFragment，则回到此Fragment
        if (mCurrentFragment != mWeiboFragment) {
            showWeiboFragment();
        } else {
            this.finish();
        }
    }
}
