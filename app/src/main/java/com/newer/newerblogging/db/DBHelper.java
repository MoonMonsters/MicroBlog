package com.newer.newerblogging.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.newer.newerblogging.application.NewerApplication;
import com.newer.newerblogging.utils.SharedPrefUtil;

/**
 * Created by Chalmers on 2016-09-15 12:57.
 * email:qxinhai@yeah.net
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = SharedPrefUtil.getUsername(NewerApplication.getInstance()) + ".db";
    public static final int VERSION = 1;

    public static final String CREATE_TABLE_SINGLEMICROBLOG = "create table microblog(" +
            "id integer primary key autoincrement," +
            "microblog text )";
    public static final String CREATE_TABLE_BLACKLIST_USER = "create table blacklist_user(" +
            "id integer primary key autoincrement," +
            "user_id text" +
            ")";
    public static final String CREATE_TABLE_BLACKLIST_WEIBO = "create table blacklist_weibo(" +
            "id integer primary key autoincrement," +
            "weibo_id text" +
            ")";

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SINGLEMICROBLOG);
        db.execSQL(CREATE_TABLE_BLACKLIST_USER);
        db.execSQL(CREATE_TABLE_BLACKLIST_WEIBO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
