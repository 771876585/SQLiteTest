package com.yezh.sqlite.sqlitetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yezh.sqlite.sqlitetest.courselessonvideo.LessonVideoPlayerActivity;
import com.yezh.sqlite.sqlitetest.pdfview.PDFActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StudentDao studentDao;
    private TextView showSQLMsg;
    private EditText inputSqlMsg;
    private ListView showDateListView;
    private List<Student> studentList;
    private StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentDao = new StudentDao(this);
        if(!studentDao.isDataExist()){
            studentDao.initTable();
        }

        initComponent();

        studentList = studentDao.getAllData();
        if(studentList != null){
            adapter = new StudentListAdapter(this, studentList);
            showDateListView.setAdapter(adapter);
        }

        //开始下载服务
       // startService(new Intent().setClass(this, UpdateService.class));

    }

    private void initComponent(){
        Button executeButton = (Button)findViewById(R.id.executeButton);
        Button insertButton = (Button)findViewById(R.id.insertButton);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        Button updateButton = (Button)findViewById(R.id.updateButton);
        Button query1Button = (Button)findViewById(R.id.query1Button);
        Button query2Button = (Button)findViewById(R.id.query2Button);
        Button query3Button = (Button)findViewById(R.id.query3Button);
        Button clickBtn = findViewById(R.id.clickBtn);

        Button btNext = (Button)findViewById(R.id.bt_next);
        btNext.setOnClickListener(this);
        Button btnTest = (Button)findViewById(R.id.bt_test);
        btnTest.setOnClickListener(this);

        executeButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        query1Button.setOnClickListener(this);
        query2Button.setOnClickListener(this);
        query3Button.setOnClickListener(this);
        clickBtn.setOnClickListener(this);

        inputSqlMsg = (EditText)findViewById(R.id.inputSqlMsg);
        showSQLMsg = (TextView)findViewById(R.id.showSQLMsg);
        showDateListView = (ListView)findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item, null), null, false);
    }

    private void refreshStudentList(){
        // 注意：千万不要直接赋值，如：studentList = studentDao.getAllData() 此时相当于重新分配了一个内存 原先的内存没改变 所以界面不会有变化
        // Java中的类是地址传递 基本数据才是值传递
        studentList.clear();
        studentList.addAll(studentDao.getAllData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.executeButton:
                showSQLMsg.setVisibility(View.GONE);
                String sql = inputSqlMsg.getText().toString();
                if (! TextUtils.isEmpty(sql)){
                    studentDao.execSQL(sql);
                }else {
                    Toast.makeText(MainActivity.this, "请输入SQL语句", Toast.LENGTH_SHORT).show();
                }

                refreshStudentList();
                break;

            case R.id.insertButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("新增一条数据：\n添加数据(6, \"测试\", \"三年级三班\", 11)\ninsert into Orders(Id, StudentName, ClassName, Age) values (6, \"测试\", \"三年级三班\", 11)");
                studentDao.insertData();
                refreshStudentList();
                break;

            case R.id.deleteButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("删除一条数据：\n删除Id为6的数据\ndelete from Student where Id = 6");
                studentDao.deleteStudent();
                refreshStudentList();
                break;

            case R.id.updateButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("修改一条数据：\n将Id为1的数据的StudentName修改了zhangsan\nupdate Student set StudentName = 'zhangsan' where Id = 1");
                studentDao.updateStudent();
                refreshStudentList();
                break;

            case R.id.query1Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                StringBuilder msg = new StringBuilder();
                msg.append("数据查询：\n此处将用户名为\"李四\"的信息提取出来\nselect * from Student where StudentName = '李四'");
                List<Student> studentList = studentDao.getStudent();
                for (Student student : studentList){
                    msg.append("\n(" + student.id + ", " + student.studentName + ", " + student.className + ", " + student.age + ")");
                }
                showSQLMsg.setText(msg);
                break;

            case R.id.query2Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                int classNameCount = studentDao.getClassNameCount();
                showSQLMsg.setText("统计查询：\n此处查询ClassName为三年级二班的用户总数\nselect count(Id) from Student where ClassName = '三年级二班'\ncount = " + classNameCount);
                break;

            case R.id.query3Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                StringBuilder msg2 = new StringBuilder();
                msg2.append("比较查询：\n此处查询单笔数据中Age最高的\nselect Id, CustomName, ClassName, Max(Age) as Age from Student");
                Student student = studentDao.getMaxStudentAge();
                msg2.append("\n(" + student.id + ", " + student.studentName + ", " + student.className + ", " + student.age + ")");
                showSQLMsg.setText(msg2);
                break;
            case R.id.bt_next:

               // startActivity(new Intent(this, WeatherActivity.class));
                //startActivity(new Intent(this, FallingActivity.class));
//                startActivity(new Intent(this, PieActivity.class));
//                startActivity(new Intent(this, WordsActivity.class));
               // startActivity(new Intent(this, PDFActivity.class));
//                startActivity(new Intent(this, ScreenActivity.class));

                break;
            case R.id.bt_test:
//                startActivity(new Intent(this, VolleyActivity.class));
//                startActivity(new Intent(this, VideoPlayerActivity.class));
//                startActivity(new Intent(this, Main2Activity.class));
//                startActivity(new Intent(this, FullWindowActivity.class));
                //startActivity(new Intent(this, LessonVideoPlayerActivity.class));
//                startActivity(new Intent(this, VolleyOkhttp3Activity.class));


                //跳转到其他app应用
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                ComponentName cn = new ComponentName("com.test.aaa",
//                        "com.test.myActivity");
//                intent.setComponent(cn);
//                startActivity(intent);
                break;
            case R.id.clickBtn:
                //startActivity(new Intent(this, NextPaperActivity.class));
                break;
            default:

                break;

        }
    }
}
