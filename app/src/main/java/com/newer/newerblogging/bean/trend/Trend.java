package com.newer.newerblogging.bean.trend;

/**
 * Created by Chalmers on 2016-09-22 19:30.
 * email:qxinhai@yeah.net
 */
public class Trend {
    private String name;
    private String query;
    private String amount;
    private String delta;

    public static final String NAME = "name";
    public static final String QUERY = "query";
    public static final String AMOUNT = "amount";
    public static final String DELTA = "delta";

    public Trend(String name, String query, String amount, String delta) {
        this.name = name;
        this.query = query;
        this.amount = amount;
        this.delta = delta;
    }

    public Trend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "Trend{" +
                "delta='" + delta + '\'' +
                ", amount='" + amount + '\'' +
                ", query='" + query + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }
}
