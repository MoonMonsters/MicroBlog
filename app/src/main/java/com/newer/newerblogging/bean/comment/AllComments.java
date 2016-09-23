package com.newer.newerblogging.bean.comment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-18 12:47.
 * email:qxinhai@yeah.net
 */
public class AllComments implements Parcelable {

    ArrayList<Comment> comments;

    public AllComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public AllComments() {
    }

    protected AllComments(Parcel in) {
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<AllComments> CREATOR = new Creator<AllComments>() {
        @Override
        public AllComments createFromParcel(Parcel in) {
            return new AllComments(in);
        }

        @Override
        public AllComments[] newArray(int size) {
            return new AllComments[size];
        }
    };

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(comments);
    }
}
