package com.newer.newerblogging.bean.microblog;

/**
 * Created by Chalmers on 2016-09-13 11:41.
 * email:qxinhai@yeah.net
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 转发微博内容
 */
public class RetweetedStatus implements Parcelable {
    /**
     * 创建时间
     */
    private String created_at;
    /**
     * 微博id
     */
    private String idstr;
    /**
     * 转发微博内容
     */
    private String text;
    /**
     * 配图图片
     */
    private ArrayList<MicroblogPic> pic_urls;
    /**
     * 用户信息
     */
    User user;
    /**
     * 转发
     */
    private int reposts_count;
    /**
     * 评论
     */
    private int comments_count;
    /**
     * 点赞
     */
    private int attitudes_count;

    public RetweetedStatus(String created_at, String idstr, String text,
                           ArrayList<MicroblogPic> pic_urls, User user,
                           int reposts_count, int comments_count, int attitudes_count) {
        this.created_at = created_at;
        this.idstr = idstr;
        this.text = text;
        this.pic_urls = pic_urls;
        this.user = user;
        this.reposts_count = reposts_count;
        this.comments_count = comments_count;
        this.attitudes_count = attitudes_count;
    }

    public RetweetedStatus() {
    }

    protected RetweetedStatus(Parcel in) {
        created_at = in.readString();
        idstr = in.readString();
        text = in.readString();
        pic_urls = in.createTypedArrayList(MicroblogPic.CREATOR);
        user = in.readParcelable(User.class.getClassLoader());
        reposts_count = in.readInt();
        comments_count = in.readInt();
        attitudes_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeString(idstr);
        dest.writeString(text);
        dest.writeTypedList(pic_urls);
        dest.writeParcelable(user, flags);
        dest.writeInt(reposts_count);
        dest.writeInt(comments_count);
        dest.writeInt(attitudes_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RetweetedStatus> CREATOR = new Creator<RetweetedStatus>() {
        @Override
        public RetweetedStatus createFromParcel(Parcel in) {
            return new RetweetedStatus(in);
        }

        @Override
        public RetweetedStatus[] newArray(int size) {
            return new RetweetedStatus[size];
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<MicroblogPic> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(ArrayList<MicroblogPic> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    @Override
    public String toString() {
        return "RetweetedStatus{" +
                "attitudes_count=" + attitudes_count +
                ", comments_count=" + comments_count +
                ", reposts_count=" + reposts_count +
                ", user=" + user +
                ", pic_urls=" + pic_urls +
                ", text='" + text + '\'' +
                ", idstr='" + idstr + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
