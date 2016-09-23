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
     * @param jsonData SingleMicroblog对象的json类型数据
     */
    public static void insertToMicroblog(String jsonData){
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FieldInterface.MICROBLOG_MICROBLOG,jsonData);
        writableDatabase.insertOrThrow(FieldInterface.MICROBLOG_TABLE,null,values);

        writableDatabase.close();
    }
}

