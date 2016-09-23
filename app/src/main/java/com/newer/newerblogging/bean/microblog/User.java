package com.newer.newerblogging.bean.microblog;

/**
 * Created by Chalmers on 2016-09-12 20:27.
 * email:qxinhai@yeah.net
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 该微博的用户信息
 */

public class User implements Parcelable {

    /**
     * 用户的id
     */
    private String idstr;
    /**
     * 用户名
     */
    private String screen_name;
    /**
     * 头像
     */
    private String avatar_hd;
    /**
     * 头像
     */
    private String profile_image_url;

    public User(String idstr, String screen_name, String avatar_hd, String profile_image_url) {
        this.idstr = idstr;
        this.screen_name = screen_name;
        this.avatar_hd = avatar_hd;
        this.profile_image_url = profile_image_url;
    }

    public User() {
    }

    protected User(Parcel in) {
        idstr = in.readString();
        screen_name = in.readString();
        avatar_hd = in.readString();
        profile_image_url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "idstr='" + idstr + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", avatar_hd='" + avatar_hd + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idstr);
        dest.writeString(screen_name);
        dest.writeString(avatar_hd);
        dest.writeString(profile_image_url);
    }
}
