package com.newer.newerblogging.bean.favorites;

import com.newer.newerblogging.bean.microblog.SingleMicroblog;

/**
 * Created by Chalmers on 2016-09-25 22:27.
 * email:qxinhai@yeah.net
 */

public class Favorite {

    private SingleMicroblog status;
    private String favorited_time;

    public Favorite(SingleMicroblog status, String favorited_time) {
        this.status = status;
        this.favorited_time = favorited_time;
    }

    public SingleMicroblog getStatus() {
        return status;
    }

    public void setStatus(SingleMicroblog status) {
        this.status = status;
    }

    public String getFavorited_time() {
        return favorited_time;
    }

    public void setFavorited_time(String favorited_time) {
        this.favorited_time = favorited_time;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "status=" + status +
                ", favorited_time='" + favorited_time + '\'' +
                '}';
    }
}
