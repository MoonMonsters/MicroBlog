package com.newer.newerblogging.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.MicroAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeWeiboFragment extends BaseFragment {

    private static final String EXTRA_USER_ID = "extra_user_id";

    private String mUserId;

//    @Bind(R.id.ptrlv_user_home_content)
//    PullToRefreshListView ptrlvUserHomeContent;

    private ArrayList<SingleMicroblog> mSingleMicroblogs;
    private MicroAdapter mMicroAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_user_home_weibo;
    }

    @Override
    public void initData() {
//        mUserId = getArguments().getString(EXTRA_USER_ID);
//        ptrlvUserHomeContent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        mSingleMicroblogs = new ArrayList<>();
//        mMicroAdapter = new MicroAdapter(getContext(), mSingleMicroblogs, Config.ACTION_USER_HOME_FRAGMENT);
//        ptrlvUserHomeContent.setAdapter(mMicroAdapter);

//        NetConnectionUtil.netToUserTimeLineStatuses(getContext(), mUserId,
//                BlogInterfaceConfig.MAX_MICRO_NUM, new NetConnectionUtil.NetCallback() {
//                    @Override
//                    public void doSuccess(String data) {
////                        mSingleMicroblogs.addAll(0, new Gson().fromJson(data, AllMicroblog.class)
////                                .getStatuses());
////                        mMicroAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void doFail(String message) {
//
//                    }
//                });
    }

    public BaseFragment getInstance(String userId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_USER_ID, userId);
        setArguments(bundle);

        return this;
    }
}