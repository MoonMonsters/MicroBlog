package com.newer.newerblogging.bean.microblog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chalmers on 2016-09-13 10:05.
 * email:qxinhai@yeah.net
 */
public class MicroblogPic implements Parcelable {

    /**
     * 微博配图 ,缩略图
     */
    private String thumbnail_pic;

    public MicroblogPic() {
    }

    public MicroblogPic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    protected MicroblogPic(Parcel in) {
        thumbnail_pic = in.readString();
    }

    public static final Creator<MicroblogPic> CREATOR = new Creator<MicroblogPic>() {
        @Override
        public MicroblogPic createFromParcel(Parcel in) {
            return new MicroblogPic(in);
        }

        @Override
        public MicroblogPic[] newArray(int size) {
            return new MicroblogPic[size];
        }
    };

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    @Override
    public String toString() {
        return "MicroblogPic{" +
                "thumbnail_pic='" + thumbnail_pic + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail_pic);
    }
}
