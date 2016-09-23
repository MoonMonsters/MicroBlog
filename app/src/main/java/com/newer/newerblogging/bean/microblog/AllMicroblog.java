package com.newer.newerblogging.bean.microblog;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-13 09:35.
 * email:qxinhai@yeah.net
 */
public class AllMicroblog {

    /**
     * 微博内容集合
     */
    private ArrayList<SingleMicroblog> statuses;
    /**
     * 标志，使用此标志，可以得到之后的微博
     */
    private String since_id;
    /**
     * 标志，可以得到此次之前的微博
     */
    private String max_id;

    public AllMicroblog(String max_id, String since_id, ArrayList<SingleMicroblog> statuses) {
        this.max_id = max_id;
        this.since_id = since_id;
        this.statuses = statuses;
    }

    public AllMicroblog() {
    }

    public ArrayList<SingleMicroblog> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<SingleMicroblog> statuses) {
        this.statuses = statuses;
    }

    public String getSince_id() {
        return since_id;
    }

    public void setSince_id(String since_id) {
        this.since_id = since_id;
    }

    public String getMax_id() {
        return max_id;
    }

    public void setMax_id(String max_id) {
        this.max_id = max_id;
    }

    @Override
    public String toString() {
        return "AllMicroblog{" +
                "statuses=" + statuses +
                ", since_id='" + since_id + '\'' +
                ", max_id='" + max_id + '\'' +
                '}';
    }
}
