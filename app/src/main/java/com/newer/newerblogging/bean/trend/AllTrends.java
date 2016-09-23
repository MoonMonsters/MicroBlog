package com.newer.newerblogging.bean.trend;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-23 09:36.
 * email:qxinhai@yeah.net
 */

public class AllTrends {

    private ArrayList<Trend> trend;

    public AllTrends(ArrayList<Trend> trend) {
        this.trend = trend;
    }

    public AllTrends(){}

    public ArrayList<Trend> getTrend() {
        return trend;
    }

    public void setTrend(ArrayList<Trend> trend) {
        this.trend = trend;
    }
}
