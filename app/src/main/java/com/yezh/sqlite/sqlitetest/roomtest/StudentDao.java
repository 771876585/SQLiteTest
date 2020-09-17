package com.yezh.sqlite.sqlitetest.roomtest;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by yezh on 2020/9/17 14:07.
 * Description：
 */
@Dao
public interface StudentDao {
    @Insert
    void insert(Student... students);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("select * from student")
    List<Student> getAll();

    @Query("select * from student where name = :name")
    List<Student> findByName(String name);

    @Query("select * from student where id in (:ids)")
    List<Student> findByIds(int[] ids);
}
