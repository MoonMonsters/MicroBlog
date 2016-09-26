package com.newer.newerblogging.bean.contact;

import com.newer.newerblogging.bean.UserInfo;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-26 10:35.
 * email:qxinhai@yeah.net
 */

public class Users {

    /**
     * 用户集合
     */
    private ArrayList<UserInfo> users;
    /**
     * 下一个游标，用来查询下一组数据
     */
    private int next_cursor;
    /**
     * 上一个游标，用来查询上一组数据
     */
    private int previous_cursor;
    /**
     * 总数
     */
    private int total_number;

    public Users(ArrayList<UserInfo> users, int next_cursor, int previous_cursor, int total_number) {
        this.users = users;
        this.next_cursor = next_cursor;
        this.previous_cursor = previous_cursor;
        this.total_number = total_number;
    }

    public Users() {
    }

    public ArrayList<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserInfo> users) {
        this.users = users;
    }

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    @Override
    public String toString() {
        return "Users{" +
                "users=" + users +
                ", next_cursor=" + next_cursor +
                ", previous_cursor=" + previous_cursor +
                ", total_number=" + total_number +
                '}';
    }
}

