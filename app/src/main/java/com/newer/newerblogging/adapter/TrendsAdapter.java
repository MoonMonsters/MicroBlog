package com.newer.newerblogging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseViewHolder;
import com.newer.newerblogging.bean.trend.Trend;
import com.newer.newerblogging.view.CircleDot;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Chalmers on 2016-09-22 19:25.
 * email:qxinhai@yeah.net
 */
public class TrendsAdapter extends BaseAdapter {

    private ArrayList<Trend> mTrends;
    private Context mContext;

    public TrendsAdapter(Context context, ArrayList<Trend> trends) {
        this.mTrends = trends;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mTrends.size();
    }

    @Override
    public Object getItem(int position) {
        return mTrends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trend, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(mTrends.get(position));

        return convertView;
    }

    class ViewHolder extends BaseViewHolder {

        @Bind(R.id.cd_trend_sort)
        CircleDot cdTrendSort;
        @Bind(R.id.tv_trend_text)
        TextView tvTrendText;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindData(Object obj) {
            Trend trend = (Trend) obj;
            cdTrendSort.setData(mTrends.indexOf(trend) + 1);
            tvTrendText.setText("#" + trend.getName() + "#");
        }
    }
}
