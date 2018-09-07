package com.yezh.sqlite.sqlitetest;

/**
 * Created by yezh on 2018/9/6 17:05.
 */

public class Student {
    public int id;
    public String studentName;
    public String className;
    public int age;

    public Student(){

    }

    public Student(int id, String studentName, String className, int age){
        this.id = id;
        this.studentName = studentName;
        this.className = className;
        this.age = age;
    }
}
