package com.example.myapplication.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.NestedGridView;
import com.example.myapplication.R;
import com.example.myapplication.goods.GoodsInfoActivity;
import com.example.myapplication.home.bean.ChannerBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 主页整个布局的适配器
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {


    //banner
    //频道
    public static final int CHANNEL=0;
    //推荐
    public static final int RECOMMEND=1;

    private int currentType=0;//当前类型

    private LayoutInflater mInflater;


    private Context context;

    public HomeFragmentAdapter(Context context) {
        this.context = context;

        //初始化各个item布局,View view=View.inflate()也是调用了这个方法初始化布局
        mInflater=LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==CHANNEL){
            View view=mInflater.inflate(R.layout.channer_viewpager, null);
            return new ChannerViewHolder(context,view);
        }else if(viewType==RECOMMEND){
            View view=mInflater.inflate(R.layout.recommed_viewpager, null);
            return new RecommedViewHolder(context,view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(getItemViewType(position)==CHANNEL){
            ChannerViewHolder channerViewHolder= (ChannerViewHolder) holder;
            channerViewHolder.gridView.setAdapter(channerViewHolder.adapter);
        }else if(getItemViewType(position)==RECOMMEND){
            RecommedViewHolder recommedViewHolder= (RecommedViewHolder) holder;
            List<ShopItemBean> data = recommedViewHolder.data;
            RecommendAdapter adapter=new RecommendAdapter(context,data);
            recommedViewHolder.gridView.setAdapter(adapter);
        }
    }


    /**
     * 系统方法：得到类型,系统会调用，有需要可以自己调用
     * @param position
     * @return 返回类型
     */
    @Override
    public int getItemViewType(int position) {
        switch (position){
            case CHANNEL:
                currentType=CHANNEL;
                break;
            case RECOMMEND:
                currentType=RECOMMEND;
                break;
        }
        return currentType;
    }

    /**
     * 总共有多少个item
     * @return
     */
    @Override
    public int getItemCount() {
        //开发过程中，每开发一个+1
        return 2;
    }




    //----------------------------------------------此处为各种item类-----------------------------------------


    private class ChannerViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private NestedGridView gridView;
        private ChannerAdapter adapter;

        public ChannerViewHolder(@NonNull final Context context, View itemView) {
            super(itemView);
            this.context=context;
            this.gridView=(NestedGridView) itemView.findViewById(R.id.gv_channer);
            List<ChannerBean> list=new ArrayList<>();
            for(int i=0;i<8;i++){
                ChannerBean bean=new ChannerBean("标题"+i);
                list.add(bean);
            }
            adapter=new ChannerAdapter(list, context);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("TAG", "分类位置："+position);

                }
            });
        }
    }

    //推荐ViewHolder
    private class RecommedViewHolder extends RecyclerView.ViewHolder {
        private NestedGridView gridView;
        private List<ShopItemBean> data;
        public RecommedViewHolder(Context context, View itemView) {
            super(itemView);
            gridView=itemView.findViewById(R.id.gv_recommend);
            data=new ArrayList<>();
            for(int i=0;i<10;i++){
                ImageView imageView=new ImageView(context);
                imageView.setImageResource(R.drawable.h);
                ShopItemBean bean=new ShopItemBean(i+100, "商品名称", imageView);
                data.add(bean);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("TAG", "推荐商品位置："+position);
                        startGoodInfoActivity(data,position);
                    }
                });
            }
        }
    }


    //------------------------------------------------------------



    //各个item点击监听方法（暂时）
    private void startGoodInfoActivity(List<ShopItemBean> data, int position) {
        Intent intent=new Intent(context, GoodsInfoActivity.class);
        ShopItemBean bean=data.get(position);
        intent.putExtra("GOOD_VALUE" ,bean.getPrice()+"");
        Log.e("TAG", "");
        context.startActivity(intent);
    }

}
