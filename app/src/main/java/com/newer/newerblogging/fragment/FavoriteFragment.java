package com.newer.newerblogging.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.MicroAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.favorites.AllFavorites;
import com.newer.newerblogging.bean.favorites.Favorite;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 抽屉中收藏Fragment
 */
public class FavoriteFragment extends BaseFragment {

    @Bind(R.id.ptrlv_favorite_content)
    PullToRefreshListView ptrlvFavoriteContent;

    private MicroAdapter mMicroAdapter;
    private ArrayList<SingleMicroblog> mSingleMicroblogs;

    /**
     * 加载的页码，用来更新数据
     */
    private int mPage = 1;
    /**
     * 每次加载5条数据
     */
    private final int COUNT = 5;

    /**
     * 广播
     */
    private FavoriteReceiver mReceiver;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void initData() {

        mSingleMicroblogs = new ArrayList<>();
        mMicroAdapter = new MicroAdapter(getContext(), mSingleMicroblogs, Config.ACTION_FAVORITE_FRAGMENT);
        ptrlvFavoriteContent.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvFavoriteContent.setAdapter(mMicroAdapter);

        //初始化加载5条数据
        refresh(COUNT, mPage++);
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();

        //注册广播
        if (mReceiver == null) {
            mReceiver = new FavoriteReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACTION_FAVORITE_FRAGMENT_LIKE);
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(mReceiver);
    }

    private void initListener() {
        ptrlvFavoriteContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mSingleMicroblogs.clear();
                mPage = 1;
                refresh(COUNT, mPage++);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(COUNT, mPage++);
            }
        });
    }

    /**
     * 加载数据
     *
     * @param count 每次加载的数据个数
     * @param page  页码
     */
    private void refresh(int count, int page) {
        NetConnectionUtil.netToUserFavorites(getContext(), count, page, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {
                ArrayList<Favorite> favorites = new Gson().fromJson(data, AllFavorites.class)
                        .getFavorites();
                for (Favorite f : favorites) {
                    mSingleMicroblogs.add(f.getStatus());
                }
                mMicroAdapter.notifyDataSetChanged();
                ptrlvFavoriteContent.onRefreshComplete();
            }

            @Override
            public void doFail(String message) {
                ptrlvFavoriteContent.onRefreshComplete();
            }
        });
    }

    /**
     * 广播
     */
    private class FavoriteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(Config.ACTION_FAVORITE_FRAGMENT_LIKE)) {
                final int position = intent.getIntExtra(Config.ACTION_DATA_POSITION, 0);
                NetConnectionUtil.netToDestroyFavorites(context, mSingleMicroblogs.get(position).getIdstr(), new NetConnectionUtil.NetCallback() {
                    @Override
                    public void doSuccess(String data) {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        mSingleMicroblogs.remove(position);
                        mMicroAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void doFail(String message) {
                        Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}