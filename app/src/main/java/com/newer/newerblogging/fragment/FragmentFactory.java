package com.newer.newerblogging.fragment;

import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.utils.Config;

import java.util.HashMap;

/**
 * Created by Chalmers on 2016-09-11 23:22.
 * email:qxinhai@yeah.net
 */

/**
 * Fragment的工厂方法类
 */
public class FragmentFactory {

    /**
     * 主页
     */
    public static final String FRAGMENT_HOME = "HomeFragment";
    /**
     * 联系人
     */
    public static final String FRAGMENT_CONTACTS = "ContactsFragment";
    /**
     * 热门
     */
    public static final String FRAGMENT_TOPICAL = "TopicalFragment";

    /**
     * 抽屉中微博
     */
    public static final String FRAGMENT_WEIBO = "WeiboFragment";
    /**
     * 抽屉中通知
     */
    public static final String FRAGMENT_NOTIFICATION = "NotificationFragment";
    /**
     * 抽屉中私信
     */
    public static final String FRAGMENT_MESSAGE = "MessageFragment";
    /**
     * 抽屉中收藏
     */
    public static final String FRAGMENT_FAVORITE = "FavoriteFragment";

    /**
     * 公共微博Fragment
     */
    public static final String FRAGMENT_PUBLIC_STATUES = "PublicStatuesFragment";
    /**
     * 话题Fragment
     */
    public static final String FRAGMENT_TRENDS = "TrendsFragment";

    /**
     * 每小时话题
     */
    public static final String FRAGMENT_TRENDS_HOURLY = "TrendsHourlyFragment";
    /**
     * 当日话题
     */
    public static final String FRAGMENT_TRENDS_DAILY = "TrendsDailyFragment";
    /**
     * 本周话题
     */
    public static final String FRAGMENT_TRENDS_WEEKLY = "TrendsWeeklyFragment";

    /**
     * 用户关注人
     */
    public static final String FRAGMENT_CONTACT_FRIENDSHIP_FRIENDS = "ContactFragment_FriendshipFriends";
    /**
     * 用户粉丝
     */
    public static final String FRAGMENT_CONTACT_FRIENDSHIP_FOLLOWERS = "ContactFragment_FriendshipFollowers";
    /**
     * 互粉
     */
    public static final String FRAGMENT_CONTACT_FRIENDSHIP_BILATERAL = "ContactFragment_FriendshipBilateral";

    /**
     * 设置Fragment
     */
    public static final String FRAGMENT_SETTING = "SettingFragment";

    private static HashMap<String, BaseFragment> mFragments = new HashMap<>();

    /**
     * 根据传入值返回Fragment对象
     *
     * @param type 需要的Fragment类型
     * @return Fragment对象
     */
    public static BaseFragment getInstance(String type) {
        BaseFragment fragment = mFragments.get(type);
        //如果在HashMap已经存在，则直接返回
        if (fragment != null) {
            return fragment;
        }

        //根据type的值，创建Fragment对象
        switch (type) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_CONTACTS:
                fragment = new ContactsFragment();
                break;
            case FRAGMENT_TOPICAL:
                fragment = new TopicalFragment();
                break;
            case FRAGMENT_WEIBO:
                fragment = new WeiboFragment();
                break;
            case FRAGMENT_NOTIFICATION:
                fragment = new NotificationFragment();
                break;
            case FRAGMENT_MESSAGE:
                fragment = new MessageFragment();
                break;
            case FRAGMENT_FAVORITE:
                fragment = new FavoriteFragment();
                break;
            case FRAGMENT_TRENDS:
                fragment = new TrendsFragment();
                break;
            case FRAGMENT_PUBLIC_STATUES:
                fragment = new PublicStatuesFragment();
                break;
            case FRAGMENT_TRENDS_HOURLY:
                fragment = new TrendContentFragment().getInstance(Config.HOURLY);
                break;
            case FRAGMENT_TRENDS_DAILY:
                fragment = new TrendContentFragment().getInstance(Config.DAILY);
                break;
            case FRAGMENT_TRENDS_WEEKLY:
                fragment = new TrendContentFragment().getInstance(Config.WEEKLY);
                break;
            case FRAGMENT_CONTACT_FRIENDSHIP_FRIENDS:
                fragment = new ContactContentFragment().getInstance(Config.FRIEDNSHIP_FRIENDS);
                break;
            case FRAGMENT_CONTACT_FRIENDSHIP_FOLLOWERS:
                fragment = new ContactContentFragment().getInstance(Config.FRIENDSHIP_FOLLOWERS);
                break;
            case FRAGMENT_CONTACT_FRIENDSHIP_BILATERAL:
                fragment = new ContactContentFragment().getInstance(Config.FRIENDSHIP_BILATERAL);
                break;
            case FRAGMENT_SETTING:
                fragment = new SettingFragment();
                break;
        }

        //放入HashMap中，下次再加载，就不用重新创建
        mFragments.put(type, fragment);

        return fragment;
    }
}
