package com.newer.newerblogging.bean.microblog;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-13 09:35.
 * email:qxinhai@yeah.net
 */

/**
 * 单条微博所有内容信息
 */
public class SingleMicroblog implements Parcelable {

    /**
     * 微博创建的时间
     */
    private String created_at;
    /**
     * 得到该条微博的id
     */
    private String idstr;
    /**
     * 微博内容
     */
    private String text;
    /**
     * 来源
     */
    private String source;
    /**
     * 是否已经收藏
     */
    private boolean favorited;
    /**
     * 微博配图
     */
    private ArrayList<MicroblogPic> pic_urls;
    /**
     * 用户信息
     */
    User user;
    /**
     * 转发数量
     */
    private int reposts_count;
    /**
     * 评论数量
     */
    private int comments_count;
    /**
     * 点赞数量
     */
    private int attitudes_count;
    /**
     * 是否已经点赞
     */
    private boolean isPraise;
    /**
     * 转发微博内容
     */
    private RetweetedStatus retweeted_status;

    public SingleMicroblog(String created_at, String idstr, String text, String source,
                           boolean favorited, ArrayList<MicroblogPic> pic_urls,
                           User user, int reposts_count, int comments_count,
                           int attitudes_count, RetweetedStatus retweeted_status) {
        this.created_at = created_at;
        this.idstr = idstr;
        this.text = text;
        this.source = source;
        this.favorited = favorited;
        this.pic_urls = pic_urls;
        this.user = user;
        this.reposts_count = reposts_count;
        this.comments_count = comments_count;
        this.attitudes_count = attitudes_count;
        this.retweeted_status = retweeted_status;
    }

    public SingleMicroblog() {
    }


    protected SingleMicroblog(Parcel in) {
        created_at = in.readString();
        idstr = in.readString();
        text = in.readString();
        source = in.readString();
        favorited = in.readByte() != 0;
        pic_urls = in.createTypedArrayList(MicroblogPic.CREATOR);
        user = in.readParcelable(User.class.getClassLoader());
        reposts_count = in.readInt();
        comments_count = in.readInt();
        attitudes_count = in.readInt();
        isPraise = in.readByte() != 0;
        retweeted_status = in.readParcelable(RetweetedStatus.class.getClassLoader());
    }

    public static final Creator<SingleMicroblog> CREATOR = new Creator<SingleMicroblog>() {
        @Override
        public SingleMicroblog createFromParcel(Parcel in) {
            return new SingleMicroblog(in);
        }

        @Override
        public SingleMicroblog[] newArray(int size) {
            return new SingleMicroblog[size];
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
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

    public RetweetedStatus getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(RetweetedStatus retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    @Override
    public String toString() {
        return "SingleMicroblog{" +
                "created_at='" + created_at + '\'' +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", favorited=" + favorited +
                ", pic_urls=" + pic_urls +
                ", user=" + user +
                ", reposts_count=" + reposts_count +
                ", comments_count=" + comments_count +
                ", attitudes_count=" + attitudes_count +
                ", isPraise=" + isPraise +
                ", retweeted_status=" + retweeted_status +
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
        dest.writeString(text);
        dest.writeString(source);
        dest.writeByte((byte) (favorited ? 1 : 0));
        dest.writeTypedList(pic_urls);
        dest.writeParcelable(user, flags);
        dest.writeInt(reposts_count);
        dest.writeInt(comments_count);
        dest.writeInt(attitudes_count);
        dest.writeByte((byte) (isPraise ? 1 : 0));
        dest.writeParcelable(retweeted_status, flags);
    }
}
