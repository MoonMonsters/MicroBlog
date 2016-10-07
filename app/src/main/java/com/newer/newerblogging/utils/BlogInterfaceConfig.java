package com.newer.newerblogging.utils;

/**
 * Created by Chalmers on 2016-09-12 12:12.
 * email:qxinhai@yeah.net
 */
public interface BlogInterfaceConfig {

    String ACCESS_TOKEN = "access_token";
    String ID = "id";
    /**
     * 根据用户id获得用户信息
     */
    String USERS_SHOW = "https://api.weibo.com/2/users/show.json";
    /**
     * 关注好友的微博时间线
     */
    String STATUES_FRIENDS_TIMELINE = "https://api.weibo.com/2/statuses/friends_timeline.json";
    /**
     * 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     */
    String SINCE_ID = "since_id";
    /**
     * 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     */
    String MAX_ID = "max_id";
    /**
     * 微博的最大编号
     */
    String MAX_MICRO_NUM = String.valueOf(Long.MAX_VALUE);

    /**
     * 收藏微博
     */
    String FAVORITES_CREATE = "https://api.weibo.com/2/favorites/create.json";
    /**
     * 取消收藏
     */
    String FAVORITES_DESTROY = "https://api.weibo.com/2/favorites/destroy.json";

    /**
     * 点赞接口
     */
    String ATTITUDES_CREATE = "https://api.weibo.com/2/attitudes/create.json";
    /**
     * 取消点赞接口
     */
    String ATTITUDES_DESTROY = "https://api.weibo.com/2/attitudes/destroy.json";

    /**
     * 根据微博id返回评论列表
     */
    String COMMENTS_SHOW = "https://api.weibo.com/2/comments/show.json";

    /**
     * 转发微博
     */
    String STATUSES_REPOST = "https://api.weibo.com/2/statuses/repost.json";

    /**
     * 评论微博
     */
    String COMMENTS_CREATE = "https://api.weibo.com/2/comments/create.json";

    /**
     * 公共微博数据接口
     */
    String STATUES_PUBLIC_TIMELINE = "https://api.weibo.com/2/statuses/public_timeline.json";

    /**
     * 每日话题排行
     */
    String TRENDS_DAILY = "https://api.weibo.com/2/trends/daily.json";

    /**
     * 每小时话题排行
     */
    String TRENDS_HOURLY = "https://api.weibo.com/2/trends/hourly.json";

    /**
     * 每周话题排行版
     */
    String TRENDS_WEEKLY = "https://api.weibo.com/2/trends/weekly.json";

    /**
     * 获取话题微博
     */
    String TRENDS_STATUSES = "https://api.weibo.com/2/search/topics.json";

    /**
     * 发布一条文字微博
     */
    String UPDATE_STATUSES = "https://api.weibo.com/2/statuses/update.json";

    /**
     * 获取某个用户最新发表的微博列表
     */
    String STATUSES_USER_TIMELINE = "https://api.weibo.com/2/statuses/user_timeline.json";
    /**
     * 批量获取用户的粉丝数、关注数、微博数
     */
    String USERS_COUNTS = "https://api.weibo.com/2/users/counts.json";

    /**
     * 关注一个用户
     */
    String CREATE_FRIENDSHIPS = "https://api.weibo.com/2/friendships/create.json";
    /**
     * 取消关注
     */
    String DESTROY_FRIENDSHIPS = "https://api.weibo.com/2/friendships/destroy.json";

    /**
     * 用户收藏列表
     */
    String USER_FAVORITES = "https://api.weibo.com/2/favorites.json";

    /**
     * 获取用户的关注列表
     */
    String FRIENDSHIPS_FRIENDS = "https://api.weibo.com/2/friendships/friends.json";

    /**
     * 获取用户的粉丝列表
     */
    String FRIENDSHIPS_FOLLOWERS = "https://api.weibo.com/2/friendships/followers.json";

    /**
     * 获取用户的双向关注列表，即互粉列表
     */
    String FRIENDSHIPS_BILATERAL = "https://api.weibo.com/2/friendships/friends/bilateral.json";

    /**
     * 删除自己发布的一条微博
     */
    String DESTROY_STATUSES = "https://api.weibo.com/2/statuses/destroy.json";

    /**
     * 删除一条自己的评论
     */
    String DESTROY_COMMENTS = "https://api.weibo.com/2/comments/destroy.json";

    /**
     * 回复一条评论
     */
    String REPLY_COMMENTS = "https://api.weibo.com/2/comments/reply.json";

    /**
     * 发布微博并且同时上传图片
     */
    String UPLOAD_STATUSES = "https://upload.api.weibo.com/2/statuses/upload.json";
}
