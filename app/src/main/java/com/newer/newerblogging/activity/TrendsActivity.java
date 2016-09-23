package com.newer.newerblogging.activity;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newer.newerblogging.R;
import com.newer.newerblogging.adapter.MicroAdapter;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.LoggerUtil;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.util.ArrayList;

import butterknife.Bind;

public class TrendsActivity extends BaseActivity {


    @Bind(R.id.ptrlv_trends_content)
    PullToRefreshListView ptrlvTrendsContent;

    private ArrayList<SingleMicroblog> mSingleMicroblogs;
    private MicroAdapter mMicroAdapter;

    private int index = 1;
    private String mTrendStr;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_trends;
    }

    @Override
    public void initData() {

        mTrendStr = getIntent().getStringExtra(Config.EXTRA_TREND);

        mSingleMicroblogs = new ArrayList<>();
        mMicroAdapter = new MicroAdapter(this, mSingleMicroblogs, Config.ACTION_TRENDS_ACTIVITY);
        ptrlvTrendsContent.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvTrendsContent.setAdapter(mMicroAdapter);
    }

    @Override
    public void initListener() {
        NetConnectionUtil.netToTrendsStatuses(this, mTrendStr, 20, index, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {
                LoggerUtil.i("TRENDS", data);
            }

            @Override
            public void doFail(String message) {

            }
        });
    }
}