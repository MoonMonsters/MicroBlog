package com.newer.newerblogging.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chalmers on 2016-09-15 13:10.
 * email:qxinhai@yeah.net
 */
public class InsertToTableUtil {

    /**
     * 向microblog表中插入数据
     *
     * @param jsonData SingleMicroblog对象的json类型数据
     */
    public static void insertToMicroblog(String jsonData) {
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FieldInterface.MICROBLOG_MICROBLOG, jsonData);
        writableDatabase.insertOrThrow(FieldInterface.MICROBLOG_TABLE, null, values);

        writableDatabase.close();
    }

    /**
     * 向黑名单用户表中插入数据
     *
     * @param user_id 要屏蔽的用户id
     */
    public static void insertToBlacklistUser(String user_id) {
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FieldInterface.BLACKLIST_USER_ID, user_id);
        writableDatabase.insert(FieldInterface.BLACKLIST_USER_TABLE, null, values);

        writableDatabase.close();
    }

    /**
     * 向黑名单微博表中插入数据
     *
     * @param weibo_id 要屏蔽的微博id
     */
    public static void insertToBlacklistWeibo(String weibo_id) {
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FieldInterface.BLACKLIST_WEIBO_ID, weibo_id);
        writableDatabase.insert(FieldInterface.BLACKLIST_WEIBO_TABLE, null, values);

        writableDatabase.close();
    }

}

