package com.newer.newerblogging.db;


import com.newer.newerblogging.application.NewerApplication;

/**
 * Created by Chalmers on 2016-09-15 13:57.
 * email:qxinhai@yeah.net
 */
public class DBHelperUtil {

    private static DBHelper mHelper;

    /**
     * 返回工具类对象
     */
    public static DBHelper getDBHelper(){
        if(mHelper == null){
            mHelper = new DBHelper(NewerApplication.getInstance());
        }

        return mHelper;
    }

}
