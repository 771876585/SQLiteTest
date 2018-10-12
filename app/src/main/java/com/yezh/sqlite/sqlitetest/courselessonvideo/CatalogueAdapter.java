package com.yezh.sqlite.sqlitetest.courselessonvideo;

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

public class CatalogueAdapter extends BaseAdapter {

    private List<VideoInfo> list;
    private LayoutInflater inflater;
    Context context;

    public CatalogueAdapter(Context context, List<VideoInfo> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public VideoInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_catalogue, null);
            holder.tv_catalogue = (TextView)convertView.findViewById(R.id.tv_catalogue);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_catalogue.setText(list.get(position).getLessonName());

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_catalogue;
    }
}
