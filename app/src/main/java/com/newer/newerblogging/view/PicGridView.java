package com.newer.newerblogging.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Chalmers on 2016-09-13 18:39.
 * email:qxinhai@yeah.net
 */
public class PicGridView extends GridView {
    public PicGridView(Context context) {
        super(context);
    }

    public PicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
