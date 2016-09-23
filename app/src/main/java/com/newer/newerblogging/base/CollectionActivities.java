package com.newer.newerblogging.base;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-11 18:22.
 * email:qxinhai@yeah.net
 */

/**
 * 管理Activity的类
 */
public class CollectionActivities {

    /** 收集所有的activity */
    private static ArrayList<BaseActivity> mBaseActivities = new ArrayList<>();

    /** 将Activity添加进集合中 */
    public static void addActivity(BaseActivity baseActivity){
        mBaseActivities.add(baseActivity);
    }

    /** 将Activity从集合中移除 */
    public static void removeActivity(BaseActivity baseActivity){
        mBaseActivities.remove(baseActivity);
    }

    /** 移除掉所有的Activity，即退出程序 */
    public static void clearToExit(){
        mBaseActivities.clear();
    }

}
