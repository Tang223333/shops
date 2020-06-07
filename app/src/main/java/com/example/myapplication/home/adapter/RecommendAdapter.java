package com.example.myapplication.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;

import java.util.List;

class RecommendAdapter extends BaseAdapter {

    private List<ShopItemBean> data;
    private Context context;

    public RecommendAdapter(Context context,List<ShopItemBean> data) {
        this.context=context;
        this.data = data;
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
            convertView=View.inflate(context, R.layout.recommend_item,null);
        }
        ShopItemBean shopItemBean=data.get(position);
        TextView textView=(TextView) convertView.findViewById(R.id.recommend_price);

        textView.setText("$:"+shopItemBean.getPrice()+"");
        return convertView;
    }
}
