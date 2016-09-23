package com.newer.newerblogging.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Chalmers on 2016-09-11 18:18.
 * email:qxinhai@yeah.net
 */

/**
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        ButterKnife.bind(this);
        //添加
        CollectionActivities.addActivity(this);

        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除
        CollectionActivities.removeActivity(this);
        ButterKnife.unbind(this);
    }

    /** 返回布局id */
    public abstract int getLayoutResource();

    /** 控件监听器 */
    public abstract void initListener();

    /** 初始化数据 */
    public abstract void initData();
}
