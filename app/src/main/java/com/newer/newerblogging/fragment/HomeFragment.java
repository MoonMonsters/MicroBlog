package com.newer.newerblogging.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.MicroAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.microblog.AllMicroblog;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.db.DeleteFromTable;
import com.newer.newerblogging.db.InsertToTableUtil;
import com.newer.newerblogging.db.SelectFromTable;
import com.newer.newerblogging.utils.BlogInterfaceConfig;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页，显示关注的人的微博
 */
public class HomeFragment extends BaseFragment {

    /**
     * 可刷新ListView
     */
    @Bind(R.id.prlv_home_content)
    PullToRefreshListView ptrlvHomeContent;
    @Bind(R.id.fabtn_home)
    FloatingActionButton fabtnHome;
    /**
     * 微博内容集合
     */
    private ArrayList<SingleMicroblog> mSingleMicroblogs;
    /**
     * 适配器
     */
    private MicroAdapter mAdapter = null;

    /**
     * 加载的数据是否需要被存入数据表中，如果是下拉刷新的数据则存，否则不存
     */
    private boolean isInsert = true;

    private HomeBroadcastReceiver mReceiver;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {

        mSingleMicroblogs = new ArrayList<>();
        //从数据表中读取缓存数据
        mSingleMicroblogs.addAll(0, SelectFromTable.getMicroblogsFromMicroblogTable());

        mAdapter = new MicroAdapter(getContext(), mSingleMicroblogs, Config.ACTION_HOME_FRAGMENT);
        //设置模式，BOTH表示即可下拉，也可上拉刷新
        ptrlvHomeContent.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvHomeContent.setAdapter(mAdapter);

        //监听器
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        注册广播
         */
        if (mReceiver == null) {
            mReceiver = new HomeBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_HOME_FRAGMENT_LIKE);
        filter.addAction(Config.ACTION_HOME_FRAGMENT_PRAISE);
        filter.addAction(Config.ACTION_HOME_FRAGMENT_COMMENT);
        filter.addAction(Config.ACTION_HOME_FRAGMENT_REPEAT);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DeleteFromTable.deleteOddDataFromMicroblog();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化控件监听器
     */
    private void initListener() {

        ptrlvHomeContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            /**
             * 下拉刷新
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到界面上的数据
                int size = mSingleMicroblogs.size();
                //获取微博的url
                //设置为true，表示这些数据可以插入到数据表中
                isInsert = true;
                //获得刷新数据
                //当下拉刷新时，通过传递第一条微博的id值，可以得到id值比它大的微博
                refreshToGetWeiboData(BlogInterfaceConfig.SINCE_ID,
                        size == 0 ? "0" : mSingleMicroblogs.get(0).getIdstr()
                );
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                int size = mSingleMicroblogs.size();

                //获得刷新数据
                //通过得到最后一条微博的id值，然后可以得到id值比它小的，即在它之前的微博
                //通过上拉刷新的微博不会存到数据表中
                refreshToGetWeiboData(BlogInterfaceConfig.MAX_ID,
                        (size == 0 ? BlogInterfaceConfig.MAX_MICRO_NUM : mSingleMicroblogs.get(size - 1).getIdstr()));
            }
        });

        fabtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 根据url获得新的数据
     */
    private void refreshToGetWeiboData(String id_command, String id) {

        //连接网络获取数据
        NetConnectionUtil.netToGetWeiboData(getContext(), id_command, id, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {
                //将接收的json格式数据转换成对象
                AllMicroblog allMicroblog = new Gson().fromJson(data, AllMicroblog.class);

                if (isInsert) {
                    //添加到界面中去
                    mSingleMicroblogs.addAll(0, allMicroblog.getStatuses());
                    //存入数据表中
                    insertDataToTable(allMicroblog.getStatuses());
                    //重新设置标志位
                    isInsert = false;
                } else {
                    //数据接收有点问题，第一条数据会重复，所以直接移除掉
                    allMicroblog.getStatuses().remove(0);
                    //添加到列表最后的位置
                    mSingleMicroblogs.addAll(mSingleMicroblogs.size() == 0 ? 0 : mSingleMicroblogs.size(), allMicroblog.getStatuses());
                }
                //刷新界面
                mAdapter.notifyDataSetChanged();

                //刷新完成
                ptrlvHomeContent.onRefreshComplete();
            }

            @Override
            public void doFail(String message) {
                //刷新完成
                ptrlvHomeContent.onRefreshComplete();
            }
        });
    }

    /**
     * 把加载的数据插入到数据库中
     */
    private void insertDataToTable(ArrayList<SingleMicroblog> smList) {
        //插入数据时，按照时间顺序，使得最新的微博的在数据表的id值最大
        //在之后的操作中会很方便的多
        for (int i = smList.size() - 1; i >= 0; i--) {
            InsertToTableUtil.insertToMicroblog(new Gson().toJson(smList.get(i)));
        }
    }

    /**
     * 接收广播，用来更新ui状态
     */
    class HomeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到的广播
            String action = intent.getAction();
            //需要更改的位置
            final int position = intent.getIntExtra(Config.ACTION_DATA_POSITION, 0);
            //是true或者false
            //例如，如果是true，那么表示收藏，否则表示取消，其他的相同
            boolean isResult = intent.getBooleanExtra(Config.ACTION_TRUE_OR_FALSE, false);

            if (action.equals(Config.ACTION_HOME_FRAGMENT_LIKE)) { //收藏广播
                if (isResult) {   //如果是收藏
                    //发送数据
                    NetConnectionUtil.netToCreateFavorites(getContext(), mSingleMicroblogs.get(position).getIdstr(), new NetConnectionUtil.NetCallback() {
                        @Override
                        public void doSuccess(String data) {
                            mSingleMicroblogs.get(position).setFavorited(true);
                            //刷新界面
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void doFail(String message) {
                            Toast.makeText(getContext(), "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {    //取消收藏
                    NetConnectionUtil.netToDestroyFavorites(getContext(), mSingleMicroblogs.get(position).getIdstr(), new NetConnectionUtil.NetCallback() {
                        @Override
                        public void doSuccess(String data) {
                            mSingleMicroblogs.get(position).setFavorited(false);
                            //刷新界面
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void doFail(String message) {
                            Toast.makeText(getContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (action.equals(Config.ACTION_HOME_FRAGMENT_PRAISE)) {    //点赞
                if (isResult) {   //如果是点赞
                    NetConnectionUtil.netToCreateAttitudes(getContext(), mSingleMicroblogs.get(position).getIdstr(), new NetConnectionUtil.NetCallback() {
                        @Override
                        public void doSuccess(String data) {
                            //设置点赞状态为true
                            mSingleMicroblogs.get(position).setPraise(true);
                            //点赞数量+1
                            mSingleMicroblogs.get(position).setAttitudes_count(
                                    mSingleMicroblogs.get(position).getAttitudes_count() + 1
                            );
                            //刷新界面
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void doFail(String message) {
                            Toast.makeText(getContext(), "点赞失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {  //取消点赞
                    NetConnectionUtil.netToDestroyAttitudes(getContext(), mSingleMicroblogs.get(position).getIdstr(),
                            new NetConnectionUtil.NetCallback() {
                                @Override
                                public void doSuccess(String data) {
                                    //设置为未点赞状态
                                    mSingleMicroblogs.get(position).setPraise(false);
                                    //点赞数量-1
                                    mSingleMicroblogs.get(position).setAttitudes_count(
                                            mSingleMicroblogs.get(position).getAttitudes_count() - 1
                                    );
                                    //刷新界面
                                    mAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void doFail(String message) {
                                    Toast.makeText(getContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }
    }
}