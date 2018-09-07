package com.yezh.sqlite.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezh on 2018/9/6 16:44.
 */

public class StudentDao {
    private static final String TAG = "StudentDao";
    //列定义
    private final String[] STUDENT_COLUMNS = new String[]{"Id", "StudentName", "ClassName", "Age"};
    private Context context;
    private MyDBHelper studentDBHelper;

    public StudentDao(Context context){
        this.context = context;
        studentDBHelper = new MyDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     * @return
     */
    public boolean isDataExist(){
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = studentDBHelper.getReadableDatabase();
            cursor = db.query(MyDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);
            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }

            if(count > 0) return true;
        }catch (Exception e){

        }
        finally {
            if(cursor != null){
                cursor.close();
            }
            if(db != null){
                db.close();
            }
        }
        return false;

    }

    /**
     * 初始化数据
     */
    public void initTable(){
        SQLiteDatabase db = null;
        try {
            db = studentDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + MyDBHelper.TABLE_NAME + " (Id, StudentName, ClassName, Age) values (1, '张三', '三年级二班', 10)");
            db.execSQL("insert into " + MyDBHelper.TABLE_NAME + " (Id, StudentName, ClassName, Age) values (2, '李四', '三年级二班',11)");
            db.execSQL("insert into " + MyDBHelper.TABLE_NAME + " (Id, StudentName, ClassName, Age) values (3, '王五', '三年级二班',10)");
            db.execSQL("insert into " + MyDBHelper.TABLE_NAME + " (Id, StudentName, ClassName, Age) values (4, '赵六', '三年级二班',9)");
            db.execSQL("insert into " + MyDBHelper.TABLE_NAME + " (Id, StudentName, ClassName, Age) values (5, '钱七', '三年级二班',10)");

            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if(db != null){
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 执行自定义SQL语句
     * @param sql
     */
    public void execSQL(String sql){
        SQLiteDatabase db = null;
        try {
            if (sql.contains("select")){
                Toast.makeText(context, "还没处理完select语句", Toast.LENGTH_SHORT).show();
            }else if (sql.contains("insert") || sql.contains("update") || sql.contains("delete")){
                db = studentDBHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
                Toast.makeText(context, "执行SQL语句成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "执行出错，请检查SQL语句", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询数据库中所有数据
     * @return
     */
    public List<Student> getAllData(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = studentDBHelper.getReadableDatabase();
            cursor = db.query(MyDBHelper.TABLE_NAME, STUDENT_COLUMNS, null, null, null, null, null);
            if(cursor.getCount() > 0){
                List<Student> studentList = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()){
                    studentList.add(parseStudent(cursor));
                }
                return studentList;
            }
        }catch (Exception e){

        }finally {
            if(cursor != null){
                cursor.close();
            }
            if(db != null){
                db.close();
            }
        }

        return null;
    }

    /**
     * 将查询到的数据转换成Student类
     * @param cursor
     * @return
     */
    private Student parseStudent(Cursor cursor){
        Student student = new Student();
        student.id = (cursor.getInt(cursor.getColumnIndex("Id")));
        student.studentName = (cursor.getString(cursor.getColumnIndex("StudentName")));
        student.className = (cursor.getString(cursor.getColumnIndex("ClassName")));
        student.age = (cursor.getInt(cursor.getColumnIndex("Age")));
        return student;
    }

    /**
     * 新增一条数据
     * @return
     */
    public boolean insertData(){
        SQLiteDatabase db = null;
        try {
            db = studentDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", 6);
            contentValues.put("StudentName", "测试");
            contentValues.put("ClassName", "三年级三班");
            contentValues.put("Age", 11);
            db.insertOrThrow(MyDBHelper.TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 删除一条数据，此处删除Id为6的数据
     * @return
     */
    public boolean deleteStudent(){
        SQLiteDatabase db = null;
        try {
            db = studentDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(MyDBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(6)});
            db.setTransactionSuccessful();
            return true;

        }catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 修改一条数据
     * @return
     */
    public boolean updateStudent(){
        SQLiteDatabase db = null;
        try {
            db = studentDBHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("StudentName", "zhangsan");
            db.update(MyDBHelper.TABLE_NAME,
                    contentValues,
                    "Id = ?",
                    new String[]{String.valueOf(1)});
            db.setTransactionSuccessful();
            return true;

        }catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 查询李四的信息
     * @return
     */
    public List<Student> getStudent(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = studentDBHelper.getReadableDatabase();

            // select * from Orders where CustomName = 'Bor'
            cursor = db.query(MyDBHelper.TABLE_NAME,
                    STUDENT_COLUMNS,
                    "StudentName = ?",
                    new String[] {"李四"},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<Student> studentList = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Student student = parseStudent(cursor);
                    studentList.add(student);
                }
                return studentList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 统计三年级二班个数
     * @return
     */
    public int getClassNameCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = studentDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(MyDBHelper.TABLE_NAME,
                    new String[]{"COUNT(Id)"},
                    "ClassName = ?",
                    new String[] {"三年级二班"},
                    null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }

    /**
     * 比较查询  此处查询单笔数据中age最大
     */
    public Student getMaxStudentAge(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = studentDBHelper.getReadableDatabase();
            // select Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders
            cursor = db.query(MyDBHelper.TABLE_NAME, new String[]{"Id", "StudentName", "ClassName", "Max(Age) as Age"}, null, null, null, null, null);

            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()) {
                    return parseStudent(cursor);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 删除某一表
     * 测试分支创建和合并功能
     */
    public void dropTable(){
        SQLiteDatabase db = studentDBHelper.getWritableDatabase();
        db.execSQL("drop table " + MyDBHelper.TABLE_NAME);
    }

    /**
     * 清空某一表
     */
    public void deleteTable(){
        SQLiteDatabase db = studentDBHelper.getWritableDatabase();
        db.execSQL("delete from " + MyDBHelper.TABLE_NAME);
    }

    /**
     * 删除数据库
     * @return
     */
    public boolean deleteDatabase(){
        return studentDBHelper.deleteDatabase(context);
    }

}
