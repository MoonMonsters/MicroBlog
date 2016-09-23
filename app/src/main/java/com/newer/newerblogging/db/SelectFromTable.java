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
     * @return SingleMicroblog集合
     */
    public static ArrayList<SingleMicroblog> getMicroblogsFromMicroblogTable(){
        ArrayList<SingleMicroblog> microblogs = new ArrayList<>();

        SQLiteDatabase readableDatabase = DBHelperUtil.getDBHelper().getReadableDatabase();
        //查询
        Cursor cursor = readableDatabase.query(FieldInterface.MICROBLOG_TABLE,
                new String[]{"id",FieldInterface.MICROBLOG_MICROBLOG}, null, null, null, null, "id desc");

        while(cursor.moveToNext()){
            String s = cursor.getString(cursor.getColumnIndex(FieldInterface.MICROBLOG_MICROBLOG));
            SingleMicroblog singleMicroblog = new Gson().fromJson(s,SingleMicroblog.class);
            microblogs.add(singleMicroblog);
        }
        readableDatabase.close();

        return microblogs;
    }

}
