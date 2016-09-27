package com.newer.newerblogging.db;

/**
 * Created by Chalmers on 2016-09-15 13:12.
 * email:qxinhai@yeah.net
 */

/**
 * 跟数据表有关的表的名称和字段的名称
 */
public interface FieldInterface {

    /**
     * microblog表名称
     */
    String MICROBLOG_TABLE = "microblog";
    /**
     * microblog表中的microblog字段
     */
    String MICROBLOG_MICROBLOG = "microblog";

    /**
     * 黑名单用户表
     */
    String BLACKLIST_USER_TABLE = "blacklist_user";
    /**
     * 黑名单用户表中的用户id
     */
    String BLACKLIST_USER_ID = "user_id";

    /**
     * 黑名单微博表
     */
    String BLACKLIST_WEIBO_TABLE = "blacklist_weibo";
    /**
     * 黑名单微博表微博id
     */
    String BLACKLIST_WEIBO_ID = "weibo_id";
}
