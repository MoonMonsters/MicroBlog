package com.newer.newerblogging.utils;

/**
 * Created by Chalmers on 2016-09-12 12:38.
 * email:qxinhai@yeah.net
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.newer.newerblogging.bean.UserInfo;


/**
 * 轻量级本地存储工具类
 */
public class SharedPrefUtil {

    /**
     * 存储名
     */
    public static final String LOCAL_USER_INFO = "local_user_info";
    /**
     * 用户large头像网址
     */
    public static final String USER_HEAD_IMG_LARGE = "user_head_img_large";
    /**
     *
     */
    public static final String USER_HEAD_IMG_MIDDLE = "user_head_img_middle";
    /**
     * 用户昵称
     */
    public static final String USERNAME = "username";
    /**
     * 描述信息
     */
    public static final String DESCRIPTION = "description";
    /**
     * 微博数量
     */
    public static final String STATUES_COUNT = "statues_count";
    /**
     * 粉丝数量
     */
    public static final String FOLLOWERS_COUNT = "followers_count";
    /**
     * 关注数量
     */
    public static final String FOLLOWING_COUNT = "following_count";

    /** 背景图 */
    public static final String COVER_BG_IMG = "cover_gb_img";
    /**
     * 保存登录用户信息
     *
     * @param context       上下文对象
     * @param localUserInfo 登录用户对象信息
     */
    public static void saveLocalUserInfo(Context context, UserInfo localUserInfo) {

        Log.i("TAG", localUserInfo.getScreen_name());

        SharedPreferences.Editor editor = context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(USER_HEAD_IMG_LARGE, localUserInfo.getAvatar_hd());
        editor.putString(USER_HEAD_IMG_MIDDLE,localUserInfo.getProfile_image_url());
        editor.putString(USERNAME, localUserInfo.getScreen_name());
        editor.putString(DESCRIPTION, localUserInfo.getDescription());
        editor.putInt(STATUES_COUNT, localUserInfo.getStatuses_count());
        editor.putInt(FOLLOWERS_COUNT, localUserInfo.getFollowers_count());
        editor.putInt(FOLLOWING_COUNT, localUserInfo.getFriends_count());
        editor.putString(COVER_BG_IMG,localUserInfo.getCover_image_phone());
        editor.apply();
    }

    /**
     * 返回用户大图头像url
     *
     * @param context 上下文对象
     */
    public static String getUserHeadImgLarge(Context context) {

        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getString(USER_HEAD_IMG_LARGE, null);
    }

    /**
     * 获得用户名
     */
    public static String getUsername(Context context) {
        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getString(USERNAME, null);
    }

    /**
     * 获得描述信息
     */
    public static String getDescription(Context context) {
        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getString(DESCRIPTION, null);
    }

    /**
     * 获得微博数量
     */
    public static int getStatuesCount(Context context) {

        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getInt(STATUES_COUNT, 0);
    }

    /**
     * 获得粉丝数量
     */
    public static int getFollowersCount(Context context) {

        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getInt(FOLLOWERS_COUNT, 0);
    }

    /**
     * 获得关注者数量
     */
    public static int getFollowingCount(Context context) {
        return context.getSharedPreferences(LOCAL_USER_INFO, Context.MODE_PRIVATE)
                .getInt(FOLLOWING_COUNT, 0);
    }

    public static String getCoverBgImg(Context context){

        return context.getSharedPreferences(LOCAL_USER_INFO,Context.MODE_PRIVATE)
                .getString(COVER_BG_IMG,null);
    }

    /**
     * 获得中等大小头像
     */
    public static String getUserHeadImgMiddle(Context context){
        return context.getSharedPreferences(LOCAL_USER_INFO,Context.MODE_PRIVATE)
                .getString(USER_HEAD_IMG_MIDDLE,null);
    }

}
