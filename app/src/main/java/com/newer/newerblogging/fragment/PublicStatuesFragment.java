package com.newer.newerblogging.fragment;


import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.MicroAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.microblog.AllMicroblog;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.utils.BlogInterfaceConfig;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.util.ArrayList;

import butterknife.Bind;


public class PublicStatuesFragment extends BaseFragment {


    @Bind(R.id.ptrlv_public_statues_content)
    PullToRefreshListView ptrlvPublicStatuesContent;

    private ArrayList<SingleMicroblog> mSingleMicroblogs;
    private MicroAdapter mMicroAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_public_statues;
    }

    @Override
    public void initData() {
        mSingleMicroblogs = new ArrayList<>();
        ptrlvPublicStatuesContent.setMode(PullToRefreshBase.Mode.BOTH);
        mMicroAdapter = new MicroAdapter(getContext(),mSingleMicroblogs, Config.ACTION_PUBLIC_STATUES_FRAGMENT);
        ptrlvPublicStatuesContent.setAdapter(mMicroAdapter);
        refresh(BlogInterfaceConfig.SINCE_ID,"0");
        initListener();
    }

    private void initListener(){
        ptrlvPublicStatuesContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(BlogInterfaceConfig.SINCE_ID,mSingleMicroblogs.get(0).getIdstr());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh(BlogInterfaceConfig.MAX_ID,mSingleMicroblogs.get(mSingleMicroblogs.size()-1).getIdstr());
            }
        });
    }

    private void refresh(final String id_command, String idStr){
        NetConnectionUtil.netToPublicTimelineStatues(getContext(), id_command, idStr,
                new NetConnectionUtil.NetCallback() {
                    @Override
                    public void doSuccess(String data) {
                        ArrayList<SingleMicroblog> arr = new Gson().fromJson(data, AllMicroblog.class)
                                .getStatuses();
                        if(id_command.equals(BlogInterfaceConfig.SINCE_ID)){
                            mSingleMicroblogs.addAll(0,arr);
                        }else{
                            mSingleMicroblogs.addAll(mSingleMicroblogs.size(),arr);
                        }

                        mMicroAdapter.notifyDataSetChanged();
                        ptrlvPublicStatuesContent.onRefreshComplete();
                    }

                    @Override
                    public void doFail(String message) {
                        ptrlvPublicStatuesContent.onRefreshComplete();
                    }
                });
    }
}
