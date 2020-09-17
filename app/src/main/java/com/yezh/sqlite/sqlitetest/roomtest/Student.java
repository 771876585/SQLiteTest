package com.yezh.sqlite.sqlitetest.roomtest;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yezh on 2020/9/17 14:03.
 * Description：
 */
//@Entity标识这个类用于建表
@Entity
public class Student {
    //主键，是否自增长
    @PrimaryKey(autoGenerate = true)
    public int id;

    //表中的字段，name表示表中的字段名
    @ColumnInfo(name="name")
    public String name;
    //表中的字段，默认用下面的字段名age
    @ColumnInfo
    public int age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
