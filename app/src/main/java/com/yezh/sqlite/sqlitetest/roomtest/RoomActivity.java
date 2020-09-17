package com.yezh.sqlite.sqlitetest.roomtest;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yezh.sqlite.sqlitetest.R;

import java.util.List;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    StudentDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        findViewById(R.id.select_all_btn).setOnClickListener(this);
        findViewById(R.id.insert_btn).setOnClickListener(this);
        findViewById(R.id.update_btn).setOnClickListener(this);
        findViewById(R.id.delete_btn).setOnClickListener(this);
        findViewById(R.id.find_btn).setOnClickListener(this);
        findViewById(R.id.id_btn).setOnClickListener(this);
        result = findViewById(R.id.resultTv);
        StudentDataBase database = Room.databaseBuilder(getApplicationContext(), StudentDataBase.class, "room").build();
        dao = database.studentDao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_all_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Student> students = dao.getAll();
                        if(students != null && students.size() > 0){
                            for(int i = 0; i < students.size(); i++){
                                Log.e("test", students.get(i).toString());
                            }
                        }

                    }
                }).start();

                break;
            case R.id.insert_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Student student = new Student();
                        student.name = "测试";
                        student.age = 22;
                        dao.insert(student);
                    }
                }).start();

                break;
            case R.id.update_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Student student = new Student();
                        student.id = 1;
                        student.name = "王五";
                        student.age = 30;
                        dao.update(student);
                    }
                }).start();
                break;
            case R.id.delete_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Student student = new Student();
                        student.id = 2;
                        student.name = "王五";
                        student.age = 30;
                        dao.delete(student);
                    }
                }).start();
                break;
            case R.id.find_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Student> students = dao.findByName("李四");
                        if(students != null && students.size() > 0){
                            for(int i = 0; i < students.size(); i++){
                                Log.e("test", students.get(i).toString());
                            }
                        }
                    }
                }).start();
                break;
            case R.id.id_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int[] ids = {1, 2};
                        List<Student> students = dao.findByIds(ids);
                        if(students != null && students.size() > 0){
                            for(int i = 0; i < students.size(); i++){
                                Log.e("test", students.get(i).toString());
                            }
                        }
                    }
                }).start();
                break;
        }
    }
}