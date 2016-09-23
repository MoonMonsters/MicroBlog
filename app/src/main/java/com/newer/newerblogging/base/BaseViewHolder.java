package com.newer.newerblogging.base;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Chalmers on 2016-09-11 18:20.
 * email:qxinhai@yeah.net
 */

/**
 * ViewHolder的基类
 */
public abstract class BaseViewHolder {

    public BaseViewHolder(View view){
        ButterKnife.bind(this,view);
    }

    /** 绑定数据 */
    public abstract void bindData(Object obj);

}
