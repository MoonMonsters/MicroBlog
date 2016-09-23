package com.newer.newerblogging.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.TrendsActivity;
import com.newer.newerblogging.adapter.TrendsAdapter;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.bean.trend.AllTrends;
import com.newer.newerblogging.bean.trend.Trend;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.NetConnectionUtil;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

public class TrendContentFragment extends BaseFragment {

    private static final String TYPE = "type";
    @Bind(R.id.lv_trend_content)
    ListView lvTrendContent;
    private TrendsAdapter mTrendsAdapter;
    private ArrayList<Trend> mTrends;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_trend_content;
    }

    @Override
    public void initData() {
        mTrends = new ArrayList<>();
        mTrendsAdapter = new TrendsAdapter(getContext(), mTrends);
        lvTrendContent.setAdapter(mTrendsAdapter);

        String type = getArguments().getString(TYPE);
        NetConnectionUtil.netToTrendsSort(getContext(), type, new NetConnectionUtil.NetCallback() {
            @Override
            public void doSuccess(String data) {
                parseJsonData(data);
                mTrendsAdapter.notifyDataSetChanged();
            }

            @Override
            public void doFail(String message) {

            }
        });
    }

    public BaseFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        setArguments(bundle);

        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }

    private void initListener() {
        lvTrendContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TrendsActivity.class);
                intent.putExtra(Config.EXTRA_TREND, mTrends.get(position).getName());
                startActivity(intent);
            }
        });
    }

    /**
     * 解析Json数据
     *
     * @param data 下载的话题数据
     */
    private void parseJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject object = jsonObject.getJSONObject("trends");

            String data2 = object.toString();
            /*
            将时间转换成其他的字符串，然后用Gson解析，死办法...
             */
            int start = data2.indexOf("{\"");
            int end = data2.indexOf("\":");
            String sub = data2.substring(start + 2, end);
            String replace = data2.replace(sub, "trend");

            //添加到界面中
            mTrends.addAll(0, new Gson().fromJson(replace, AllTrends.class).getTrend());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
