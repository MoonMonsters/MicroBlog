package com.newer.newerblogging.bean.favorites;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-25 22:26.
 * email:qxinhai@yeah.net
 */

public class AllFavorites {

    private ArrayList<Favorite> favorites;
    private String total_number;

    public AllFavorites(ArrayList<Favorite> favorites, String total_number) {
        this.favorites = favorites;
        this.total_number = total_number;
    }

    public AllFavorites() {
    }

    public ArrayList<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    @Override
    public String toString() {
        return "AllFavorites{" +
                "total_number='" + total_number + '\'' +
                ", favorites=" + favorites +
                '}';
    }
}
