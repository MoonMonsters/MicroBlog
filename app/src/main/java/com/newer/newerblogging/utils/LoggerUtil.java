package com.newer.newerblogging.utils;

import android.util.Log;

/**
 * Created by Chalmers on 2016-09-15 09:54.
 * email:qxinhai@yeah.net
 */

/**
 * 打印日志帮助类
 */
public class LoggerUtil {

    private static boolean isPrint = true;

    public static void i(String key, String value){
        if(isPrint){
            Log.i(key,value);
        }
    }

}
