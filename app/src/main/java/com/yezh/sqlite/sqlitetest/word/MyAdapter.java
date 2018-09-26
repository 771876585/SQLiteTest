package com.yezh.sqlite.sqlitetest.word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yezh.sqlite.sqlitetest.R;

import java.util.List;

/**
 * Created by yezh on 2018/9/19 18:21.
 */

public class MyAdapter extends BaseAdapter {

    private List<Person> list;
    private LayoutInflater inflater;
    Context context;

    public MyAdapter(Context context, List<Person> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Person getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_word, null);
            holder.tv_word = (TextView)convertView.findViewById(R.id.tv_word);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        String word = list.get(position).getHeaderWord();
        holder.tv_word.setText(word);
        holder.tv_name.setText(list.get(position).getName());
        if(position == 0){
            holder.tv_word.setVisibility(View.VISIBLE);
        }else{
            String headerword = list.get(position - 1).getHeaderWord();
            if(word.equals(headerword)){
                holder.tv_word.setVisibility(View.GONE);
            }else{
                holder.tv_word.setVisibility(View.VISIBLE);
            }
        }


        return convertView;
    }

    private class ViewHolder{
        private TextView tv_word;
        private TextView tv_name;
    }
}
