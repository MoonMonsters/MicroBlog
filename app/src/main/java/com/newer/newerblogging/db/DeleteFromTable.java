package com.newer.newerblogging.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chalmers on 2016-09-15 14:35.
 * email:qxinhai@yeah.net
 */
public class DeleteFromTable {

    /**
     * 删除microblog表中所有数据
     */
    public static void clearMicroblog(){
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();
        writableDatabase.delete(FieldInterface.MICROBLOG_TABLE,null,null);
    }

    /**
     * 从Microblog表中删除多余25条的数据
     */
    public static void deleteOddDataFromMicroblog(){
        SQLiteDatabase writableDatabase = DBHelperUtil.getDBHelper().getWritableDatabase();

        Cursor cursor = writableDatabase.query(FieldInterface.MICROBLOG_TABLE,new String[]{"id",FieldInterface.MICROBLOG_MICROBLOG},null,null,null,null,"id desc");
        int count = cursor.getCount();

        if(count >= 25){
            if(cursor.moveToNext()) {
                int curId = cursor.getInt(cursor.getColumnIndex("id"));
                writableDatabase.delete(FieldInterface.MICROBLOG_TABLE, "id<=?", new String[]{String.valueOf(curId - 25)});
            }
        }

        cursor.close();
    }
}
