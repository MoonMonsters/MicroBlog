package com.newer.newerblogging.bean;

import java.io.Serializable;

/**
 * Created by Chalmers on 2016-09-12 10:07.
 * email:qxinhai@yeah.net
 */

/**
 * 存储的数据，可以用来在主页中显示
 */
public class UserInfo implements Serializable{
    /*
    "id": 1404376560,
    "screen_name": "zaku",
    "name": "zaku",
    "province": "11",
    "city": "5",
    "location": "北京 朝阳区",
    "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。",
    "url": "http://blog.sina.com.cn/zaku",
    "profile_image_url": "http://tp1.sinaimg.cn/1404376560/50/0/1",
    "domain": "zaku",
    "gender": "m",
    "followers_count": 1204,
    "friends_count": 447,
    "statuses_count": 2908,
    "favourites_count": 0,
    "created_at": "Fri Aug 28 00:00:00 +0800 2009",
    "following": false,
    "allow_all_act_msg": false,
    "geo_enabled": true,
    "verified": false,

    "allow_all_comment": true,
    "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
    "verified_reason": "",
    "follow_me": false,
    "online_status": 0,
    "bi_followers_count": 215
    "allow_all_comment": true,
    "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
    "verified_reason": "",
    "follow_me": false,
    "online_status": 0,
    "bi_followers_count": 215
     */
    /** 用户UID */
    private int id;
    /** 用户昵称 */
    private String screen_name;
    /** 友好显示名称 */
    private String name;
    /** 用户所在省级ID */
    private String province;
    /** 用户所在城市ID */
    private String city;
    /** 用户所在地 */
    private String location;
    /** 用户个人描述 */
    private String description;
    /** 用户博客地址 */
    private String url;
    /** 用户头像地址（中图），50×50像素 */
    private String profile_image_url;
    /** 封面图 */
    private String cover_image_phone;
    /** 用户的个性化域名 */
    private String domain;
    /** 性别，m：男、f：女、n：未知 */
    private String gender;
    /** 粉丝数 */
    private int followers_count;
    /** 关注数 */
    private int friends_count;
    /** 微博数 */
    private int statuses_count;
    /** 收藏数 */
    private int favourites_count;
    /** 用户创建（注册）时间 */
    private String created_at;
    /** */
    private boolean following;
    /** 是否允许所有人给我发私信，true：是，false：否 */
    private boolean allow_all_act_msg;
    /** 是否允许标识用户的地理位置，true：是，false：否 */
    private boolean geo_enabled;
    /** 是否是微博认证用户，即加V用户，true：是，false：否 */
    private boolean verified;
    /** 是否允许所有人对我的微博进行评论，true：是，false：否 */
    private boolean allow_all_comment;
    /** 用户头像地址（大图），180×180像素 */
    private String avatar_large;
    /** 认证原因 */
    private String verified_reason;
    /** 该用户是否关注当前登录用户，true：是，false：否 */
    private boolean follow_me;
    /** 用户的在线状态，0：不在线、1：在线 */
    private int online_status;
    /** 用户的互粉数 */
    private int bi_followers_count;

    private String avatar_hd;

    public UserInfo(int id, String screen_name, String name, String province,
                    String city, String location, String description, String url,
                    String profile_image_url, String domain, String gender,
                    int followers_count, int friends_count, int statuses_count,
                    int favourites_count, String created_at, boolean following,
                    boolean allow_all_act_msg, boolean geo_enabled, boolean verified,
                    boolean allow_all_comment, String avatar_large,
                    String verified_reason, boolean follow_me, int online_status,
                    int bi_followers_count, String avatar_hd, String cover_image_phone) {
        this.id = id;
        this.screen_name = screen_name;
        this.name = name;
        this.province = province;
        this.city = city;
        this.location = location;
        this.description = description;
        this.url = url;
        this.profile_image_url = profile_image_url;
        this.domain = domain;
        this.gender = gender;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
        this.statuses_count = statuses_count;
        this.favourites_count = favourites_count;
        this.created_at = created_at;
        this.following = following;
        this.allow_all_act_msg = allow_all_act_msg;
        this.geo_enabled = geo_enabled;
        this.verified = verified;
        this.allow_all_comment = allow_all_comment;
        this.avatar_large = avatar_large;
        this.verified_reason = verified_reason;
        this.follow_me = follow_me;
        this.online_status = online_status;
        this.bi_followers_count = bi_followers_count;
        this.avatar_hd = avatar_hd;
        this.cover_image_phone = cover_image_phone;
    }

    public UserInfo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(int statuses_count) {
        this.statuses_count = statuses_count;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(int favourites_count) {
        this.favourites_count = favourites_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public void setAllow_all_act_msg(boolean allow_all_act_msg) {
        this.allow_all_act_msg = allow_all_act_msg;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public void setAllow_all_comment(boolean allow_all_comment) {
        this.allow_all_comment = allow_all_comment;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public void setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public void setFollow_me(boolean follow_me) {
        this.follow_me = follow_me;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public int getBi_followers_count() {
        return bi_followers_count;
    }

    public void setBi_followers_count(int bi_followers_count) {
        this.bi_followers_count = bi_followers_count;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getCover_image_phone() {
        return cover_image_phone;
    }

    public void setCover_image_phone(String cover_image_phone) {
        this.cover_image_phone = cover_image_phone;
    }
}
