package com.newer.newerblogging.utils;

/**
 * 授权时所需要的常量
 */
public interface Constants {

    /**  应用的APP_KEY */
    String APP_KEY = "2530994351";
    /** 应用的回调页 */
    String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /** 应用申请的 高级权限 */
    String SCOPE = "email,direct_messages_read,direct_messages_write,"
        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
        + "follow_app_official_microblog," + "invitation_write";

}