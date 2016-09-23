package com.newer.newerblogging.bean.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.newer.newerblogging.bean.microblog.User;


/**
 * Created by Chalmers on 2016-09-18 12:47.
 * email:qxinhai@yeah.net
 */
public class Comment implements Parcelable {

    /**
     * 时间
     */
    String created_at;
    /**
     * id
     */
    String idstr;
    /**
     * 貌似没有用，指的是楼层（第几个发言）
     */
    String floor_number;
    /**
     * 内容
     */
    String text;
    /**
     * 用户
     */
    User user;

    public Comment(String created_at, String idstr, String floor_number, String text, User user) {
        this.created_at = created_at;
        this.idstr = idstr;
        this.floor_number = floor_number;
        this.text = text;
        this.user = user;
    }

    protected Comment(Parcel in) {
        created_at = in.readString();
        idstr = in.readString();
        floor_number = in.readString();
        text = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(String floor_number) {
        this.floor_number = floor_number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "created_at='" + created_at + '\'' +
                ", idstr='" + idstr + '\'' +
                ", floor_number='" + floor_number + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeString(idstr);
        dest.writeString(floor_number);
        dest.writeString(text);
        dest.writeParcelable(user, flags);
    }
}
