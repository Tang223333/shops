package com.example.myapplication.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;
import com.example.myapplication.home.bean.ChannerBean;

import java.util.List;


public class ChannerAdapter extends BaseAdapter {


    private List<ChannerBean> data;
    private Context context;


    public ChannerAdapter(List data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.channer_item , null);

        }
        ChannerBean bean=data.get(position);
        TextView textView=(TextView) convertView.findViewById(R.id.tv_channer);
        textView.setText(bean.getTitle());
        return convertView;
    }
}
