package com.newer.newerblogging.fragment;


import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.ContactAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.UserInfo;
import com.newer.newerblogging.bean.contact.Users;
import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.util.ArrayList;

import butterknife.Bind;

public class ContactContentFragment extends BaseFragment {

    private final String TYPE = "type";
    @Bind(R.id.ptrlv_contact_content)
    PullToRefreshListView ptrlvContactContent;

    private ArrayList<UserInfo> mUserList;
    private ContactAdapter mContactAdapter;

    /**
     * Fragment类型
     */
    private String mType;
    /**
     * 取数据用
     */
    int mPage = 0;
    /**
     * z
     * 每次取20条数据
     */
    final int COUNT = 20;
    /**
     * 游标
     */
    private int mCursor = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_contact_content;
    }

    @Override
    public void initData() {
        mType = getArguments().getString(TYPE);
        mUserList = new ArrayList<>();
        mContactAdapter = new ContactAdapter(getContext(), mUserList);
        ptrlvContactContent.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvContactContent.setAdapter(mContactAdapter);

        refreshType();
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }

    private void initListener() {
        ptrlvContactContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mUserList.clear();
                mPage = 0;
                mCursor = 0;
                refreshType();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshType();
            }
        });
    }

    private void refreshType() {
        if (mType.equals(Config.FRIENDSHIP_BILATERAL)) {
            refresh(mPage);
        } else {
            refresh(mCursor);
        }
    }

    public BaseFragment getInstance(String type) {

        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        setArguments(bundle);

        return this;
    }

    private void refresh(int page) {
        NetConnectionUtil.netToFriendShips(getContext(), mType,
                AccessTokenKeeper.readAccessToken(getContext()).getUid(),
                COUNT, page, new NetConnectionUtil.NetCallback() {
                    @Override
                    public void doSuccess(String data) {
                        Users user = new Gson().fromJson(data, Users.class);
                        mUserList.addAll(user.getUsers());

                        if (mType.equals(Config.FRIENDSHIP_BILATERAL)) {
                            mPage++;
                        } else {
                            mCursor = user.getNext_cursor();
                        }

                        mContactAdapter.notifyDataSetChanged();
                        ptrlvContactContent.onRefreshComplete();
                    }

                    @Override
                    public void doFail(String message) {
                        ptrlvContactContent.onRefreshComplete();
                    }
                });
    }
}
