package com.newer.newerblogging.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chalmers on 2016-09-20 20:27.
 * email:qxinhai@yeah.net
 */
public class Utils {

    /**
     * 将GMT时间转换成北京时间
     *
     * @param gmtTime gmt时间
     * @return 北京时间
     */
    public static String gmtToLocalTime(String gmtTime) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        try {
            Date date = sdf.parse(gmtTime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

}
