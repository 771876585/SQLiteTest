package com.yezh.sqlite.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 主要用于建数据库和建表
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student.db";
    public static final String TABLE_NAME = "Student";

    public MyDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME +
                "(Id integer primary key, StudentName text, ClassName text, Age integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 删除数据库
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context){
        return context.deleteDatabase(DB_NAME);
    }
}
