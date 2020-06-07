package com.example.myapplication.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.example.myapplication.goods.GoodsInfoActivity;

import java.util.List;

/**
 * @Author：xiangzai
 * @Date：2020/4/20 20:55
 */
public class BannerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageView> data;
    private Handler handler;

    public BannerAdapter(Context context, List<ImageView> data, Handler handler) {
        this.context = context;
        this.data = data;
        this.handler=handler;
    }


    /**
     * @return 得到图片的总数
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }


    /***
     *比较View和Oject是否同一实例
     * @param view 页面
     * @param object 这个方法instantiateItem返回的结果
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    /**
     *释放资源，因为Viewpage最多加载3个页面。开始是加载两个。果然不释放资源切换之后会报错闪退。
     * @param container Viewpager
     * @param position 要释放的位置
     * @param object 要释放的页面
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    /**
     *相当于getView方法
     * @param container Viewpager自身
     * @param position 当前实例化位置
     * @return
     */
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //求余得到位置
        int realPosition=position%data.size();

        ImageView imageView=data.get(realPosition);
        container.addView(imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://手指按下
                        Log.e("TAG", "手指点击");
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        Log.e("TAG", "手指移动");
                        break;
                    case MotionEvent.ACTION_UP://手指离开
                        //在这里设置点击监听
                        Log.e("TAG", "手指离开");
                        startGoodInfoActivity();
                        break;
                    case MotionEvent.ACTION_CANCEL://页面拖后时，会触发对上一个页面操作的取消，因为上个页面触发了按下，但是按下和离开时绑定一起的连贯的动作
                        Log.e("TAG", "页面取消");
                        break;
                }
                return true;
            }
        });

        return imageView;

    }

    //各个item点击监听方法（暂时）
    private void startGoodInfoActivity() {
        Intent intent=new Intent(context, GoodsInfoActivity.class);
        context.startActivity(intent);
    }


}

