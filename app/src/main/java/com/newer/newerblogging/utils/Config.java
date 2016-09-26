package com.newer.newerblogging.utils;

/**
 * Created by Chalmers on 2016-09-13 18:15.
 * email:qxinhai@yeah.net
 */
public interface Config {

    /**
     * 传递的图片的url
     */
    String IMAGE_LIST = "image_list";
    /**
     * 点击图片时的位置
     */
    String IMAGE_POSITION = "image_position";

    /**
     * HomeFragment的命令
     */
    String ACTION_HOME_FRAGMENT = "action_fragment_home";
    /**
     * HomeFragment的微博的收藏广播
     */
    String ACTION_HOME_FRAGMENT_LIKE = "action_home_fragment_like";

    /**
     * 删除微博广播
     */
    String ACTION_HOME_FRAGMENT_DELETE = "action_home_fragment_delete";

    /**
     * HomeFragment的微博的点赞广播 ，点赞数+1
     */
    String ACTION_HOME_FRAGMENT_PRAISE = "action_home_fragment_praise";
    /**
     * HomeFragment的微博的评论广播，当接收到此广播时，评论+1
     */
    String ACTION_HOME_FRAGMENT_COMMENT = "action_home_fragment_comment";
    /**
     * HomeFragment的微博的转发广播，转发数+1
     */
    String ACTION_HOME_FRAGMENT_REPEAT = "action_home_fragment_repeat";
    /**
     * 更改PublicStatuesFragment中的控件状态
     */
    String ACTION_PUBLIC_STATUES_FRAGMENT = "action_public_statues_fragment";
    /**
     * 更改TrendsActivity中的控件状态
     */
    String ACTION_TRENDS_ACTIVITY = "action_trends_activity";
    /**
     * 更改FavoriteFragment的控件状态
     */
    String ACTION_FAVORITE_FRAGMENT = "action_favorite_fragment";

    /**
     * 更改FavoriteFragment中的收藏按钮状态
     */
    String ACTION_FAVORITE_FRAGMENT_LIKE = "action_favorite_fragment_like";

    /**
     * 更改UserHomeFragment中的控件状态
     */
    String ACTION_USER_HOME_FRAGMENT = "action_user_home_fragment";

    /**
     * 需要更改数据的位置
     */
    String ACTION_DATA_POSITION = "action_data_position";
    /**
     * 　执行的命令，是点赞（收藏）还是取消操作
     */
    String ACTION_TRUE_OR_FALSE = "action_true_or_false";

    /**
     * 成功，例如收藏，点赞等操作
     */
    String SUCCESS = "success";
    /**
     * 失败
     */
    String FAIL = "fail";

    /**
     * 微博的id
     */
    String WEIBO_IDSTR = "weibo_idStr";
    /**
     * 转发的微博内容
     */
    String WEIBO_CONTENT = "weibo_content";

    /**
     * 传递微博数据
     */
    String EXTRA_MICROBLOG = "extra_microblog";
    /**
     * 传递的Intent中Bundle
     */
    String EXTRA_MICROBLOG_BUNDLE = "extra_microblog_bundle";

    /**
     * 每周话题
     */
    String WEEKLY = "weekly";
    /**
     * 每小时话题
     */
    String HOURLY = "hourly";
    /**
     * 每日话题
     */
    String DAILY = "daily";

    /**
     * 话题字段，传递数据
     */
    String EXTRA_TREND = "extra_trend";

    /**
     * 传递User数据
     */
    String EXTRA_USER_ID = "extra_user_id";

    /**
     * 用户关注人
     */
    String FRIEDNSHIP_FRIENDS = "friendship_friends";
    /**
     * 用户粉丝
     */
    String FRIENDSHIP_FOLLOWERS = "friendship_followers";
    /**
     * 用户互粉
     */
    String FRIENDSHIP_BILATERAL = "friendship_bilateral";
}
