package com.newer.newerblogging.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.newer.newerblogging.bean.microblog.SingleMicroblog;

import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-09-15 13:56.
 * email:qxinhai@yeah.net
 */
public class SelectFromTable {

    /**
     * 从microblog表中取出所有数据
     *
     * @return SingleMicroblog集合
     */
    public static ArrayList<SingleMicroblog> getMicroblogsFromMicroblogTable() {
        ArrayList<SingleMicroblog> microblogs = new ArrayList<>();

        SQLiteDatabase readableDatabase = DBHelperUtil.getDBHelper().getReadableDatabase();
        //查询
        Cursor cursor = readableDatabase.query(FieldInterface.MICROBLOG_TABLE,
                new String[]{"id", FieldInterface.MICROBLOG_MICROBLOG}, null, null, null, null, "id desc");

        while (cursor.moveToNext()) {
            String s = cursor.getString(cursor.getColumnIndex(FieldInterface.MICROBLOG_MICROBLOG));
            SingleMicroblog singleMicroblog = new Gson().fromJson(s, SingleMicroblog.class);
            microblogs.add(singleMicroblog);
        }
        readableDatabase.close();

        return microblogs;
    }

    /**
     * 判断该条微博是否在黑名单中存在
     *
     * @param weibo_id 微博id
     * @return 返回结果，true表示存在
     */
    public static boolean isExitFromBlacklistWeibo(String weibo_id) {
        boolean result = false;
        SQLiteDatabase readableDatabase = DBHelperUtil.getDBHelper().getReadableDatabase();
        Cursor cursor = readableDatabase.query(FieldInterface.BLACKLIST_WEIBO_TABLE, null, FieldInterface.BLACKLIST_WEIBO_ID + "=", new String[]{weibo_id}, null, null, null);
        if (cursor.moveToNext()) {
            result = true;
        }

        return result;
    }

    /**
     * 判断该用户是否在黑名单中存在
     *
     * @param user_id 用户id
     * @return 返回结果，true表示存在
     */
    public static boolean isExitFromBlacklistUser(String user_id) {
        boolean result = false;

        return result;
    }

}
