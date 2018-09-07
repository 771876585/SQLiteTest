package com.yezh.sqlite.sqlitetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yezh on 2018/9/6 17:46.
 */

public class StudentListAdapter extends BaseAdapter {

    private Context context;
    private List<Student> studentList;

    public StudentListAdapter(Context context, List<Student> studentList){
        this.context = context;
        this.studentList = studentList;
    }
    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Student student = studentList.get(position);
        if(student == null){
            return null;
        }
        ViewHolder holder = null;
        if (view != null){
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.show_sql_item, null);

            holder = new ViewHolder();
            holder.dateIdTextView = (TextView) view.findViewById(R.id.dateIdTextView);
            holder.dateCustomTextView = (TextView) view.findViewById(R.id.dateCustomTextView);
            holder.dateOrderPriceTextView = (TextView) view.findViewById(R.id.dateOrderPriceTextView);
            holder.dateCountoryTextView = (TextView) view.findViewById(R.id.dateCountoryTextView);

            view.setTag(holder);
        }

        holder.dateIdTextView.setText(student.id + "");
        holder.dateCustomTextView.setText(student.studentName);
        holder.dateOrderPriceTextView.setText(student.className);
        holder.dateCountoryTextView.setText(student.age + "");

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder{
        public TextView dateIdTextView;
        public TextView dateCustomTextView;
        public TextView dateOrderPriceTextView;
        public TextView dateCountoryTextView;
    }
}
