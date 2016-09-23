package com.newer.newerblogging.application;

import android.app.Application;
import android.text.TextUtils;

import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.LoggerUtil;
import com.newer.newerblogging.utils.NetConnectionUtil;


/**
 * Created by Chalmers on 2016-09-12 12:00.
 * email:qxinhai@yeah.net
 */
public class NewerApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        final String uid = AccessTokenKeeper.readAccessToken(this).getUid();
        LoggerUtil.i("TAG","uid = " + uid);
        if(!TextUtils.isEmpty(uid)){
            NetConnectionUtil.netGetLocalUserInfo(instance,uid);
        }
    }

    public static Application getInstance(){

        return instance;
    }
}
