package com.yezh.sqlite.sqlitetest.roomtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


/**
 * Created by yezh on 2020/9/17 14:12.
 * Description：
 */
@Database(entities = {Student.class}, version = 1)
public abstract class StudentDataBase extends RoomDatabase {
    public abstract StudentDao studentDao();
}
