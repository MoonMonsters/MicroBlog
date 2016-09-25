package com.newer.newerblogging.bean;

/**
 * Created by Chalmers on 2016-09-24 14:51.
 * email:qxinhai@yeah.net
 * <p>
 * 用户的关注数，粉丝数以及微博数
 */

/**
 * 用户的关注数，粉丝数以及微博数
 */

public class UserCounts {

    /**
     * id 不明
     */
    private long id;
    /**
     * 粉丝数量
     */
    private String followers_count;
    /**
     * 关注数量
     */
    private String friends_count;
    /**
     * 微博数量
     */
    private String statuses_count;

    public UserCounts(long id, String followers_count, String friends_count, String statuses_count) {
        this.id = id;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
        this.statuses_count = statuses_count;
    }

    public UserCounts() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(String friends_count) {
        this.friends_count = friends_count;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(String statuses_count) {
        this.statuses_count = statuses_count;
    }

    @Override
    public String toString() {
        return "UserCounts{" +
                "id='" + id + '\'' +
                ", followers_count='" + followers_count + '\'' +
                ", friends_count='" + friends_count + '\'' +
                ", statuses_count='" + statuses_count + '\'' +
                '}';
    }
}
