package com.example.myapplication.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.home.adapter.BannerAdapter;
import com.example.myapplication.home.adapter.HomeFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragemnt extends BaseFragemt {

    //banner发送循环播放
    private static final int GET_TIME = 0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==GET_TIME) {
                //得到viewpager当前页面值+1
                int currentItem = banner_viewpager.getCurrentItem() + 1;
                //设置当前页面值
                banner_viewpager.setCurrentItem(currentItem);
                //循环发延时消息
                handler.sendEmptyMessageDelayed(GET_TIME, 3000);
            }
        }
    };

    private RecyclerView recyclerView;
    private HomeFragmentAdapter adapter;


    private ViewPager banner_viewpager;
    private TextView banner_title;
    private LinearLayout banner_point;
    private BannerAdapter bannerAdapter;
    //得到上一个位置
    private int prePosition=0;

    //banner本地数据操作
    private List<ImageView> imageViews;



    @Override
    protected int loadView() {
        return R.layout.fragment_home;
    }

    //实例化
    private void initLayout() {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_home);
        imageViews=new ArrayList<ImageView>();
        banner_viewpager=(ViewPager)view.findViewById(R.id.banner_viewpager);
        banner_title=(TextView)view.findViewById(R.id.banner_title);
        banner_point=(LinearLayout)view.findViewById(R.id.banner_point);

//        return view;
    }



    //-------------本地操作--------------------

    @Override
    public void initData() {
        super.initData();

        initLayout();

        //加载数据
        processData();

        //轮播图本地数据展示
        bannerShow();

        initListener();
        //轮播图发送延时消息
        handler.sendEmptyMessageDelayed(GET_TIME, 3000);

        //下面代码用于联网请求（暂时没写）
    }


    private void initListener() {
        banner_viewpager.addOnPageChangeListener(new BannerPageChangeListener());
    }


    private void processData() {
        //联网请求到json数据
        // 判断是否请求到数据
        //由于还没还没服务器，所以暂时模拟有数据的请假
        //如果请求到数据，设置适配器
        adapter=new HomeFragmentAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }



    private void bannerShow() {

        for(int i=0;i<5;i++){
            ImageView imageView=new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.ic_launcher_foreground);
            imageViews.add(imageView);

            //下面点操作，包括添加点，设置颜色，设置间距。还有就是通过viewpager改变颜色
            ImageView point_main=new ImageButton(getContext());
            point_main.setBackgroundResource(R.drawable.point_background);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
            if(i==0){
                point_main.setEnabled(true);
            }else {
                point_main.setEnabled(false);
                layoutParams.leftMargin=60;
            }
            point_main.setLayoutParams(layoutParams);
            banner_point.addView(point_main);
            //----------------------------------------------//
        }
        bannerAdapter= new BannerAdapter(getContext(),imageViews,handler);
        banner_viewpager.setAdapter(bannerAdapter);


    }

    private boolean isDragging;
    //banner滑动监听
    class BannerPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         *当页面滑动的时候回调这个方法
         * @param position 当前页面的位置
         * @param positionOffset 滑动页面的百分比
         * @param positionOffsetPixels  在屏幕上滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition=position%imageViews.size();

            banner_point.getChildAt(prePosition).setEnabled(false);
            banner_point.getChildAt(realPosition).setEnabled(true);

            prePosition=realPosition;


        }
        /**
         *当某个页面呗选中了回调
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            int realPosition=position%imageViews.size();
        }
        /**
         * 当页面滑动状态变化的时候回调这个方法
         * 静止-》滑动  滑动-》静止  静止-》拖拽
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if(state==ViewPager.SCROLL_STATE_IDLE){//静止的时候
//                Log.e("TAG", "SCROLL_STATE_IDLE");
                isDragging=true;
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(GET_TIME, 5000);

            }else if(state==ViewPager.SCROLL_STATE_DRAGGING&&isDragging){//拉的时候
                isDragging=false;
//                Log.e("TAG", "SCROLL_STATE_DRAGGING");
                handler.removeCallbacksAndMessages(null);

            }else if(state==ViewPager.SCROLL_STATE_SETTLING){//用户手放开的时候
                //  Log.e("TAG", "SCROLL_STATE_SETTLING");

            }
        }
    }

}
