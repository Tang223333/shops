package com.example.myapplication.shop.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;


/**
 * 定义一个类来做购物车的数量增减布局
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView iv_add;
    private ImageView iv_sub;
    private TextView tv_velues;
    //默认值，这里我先设置为1
    private int value=1;
    //最大值，最小值
    private int MAX_VALUES=10;
    private int MIN_VALUES=1;
    private Context context;

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context=context;

        //把布局实例化化，并且加载到AddSubView中
        View.inflate(context, R.layout.add_sub_view, this);

        iv_add=(ImageView)findViewById(R.id.iv_add);
        iv_sub=(ImageView)findViewById(R.id.iv_sub);
        tv_velues=(TextView)findViewById(R.id.tv_velues);


        int value=getValue();
        setValue(value);


//        设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add://点击增加
                addNumber();
                break;
            case R.id.iv_sub://点击减少
                subNumber();
                break;
        }

    }

    //数量增加
    private void addNumber() {
        if(value<MAX_VALUES){
            value = getValue();
            value++;
            setValue(value);
        }
        if(onNumberChangeListener!=null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }

    //数量减少
    private void subNumber() {
        if(value>MIN_VALUES){
            value= getValue();
            value--;
            setValue(value);
        }
        if(onNumberChangeListener!=null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }


    public int getValue() {
        String valuesStr=tv_velues.getText().toString().trim();
        if(!TextUtils.isEmpty(valuesStr)){
            value=Integer.parseInt( valuesStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_velues.setText(value+"");
    }


    //当数量发生变化时回调
    public interface OnNumberChangeListener{

        public void OnNumberChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    //设置数量变化监听
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener){
        this.onNumberChangeListener=onNumberChangeListener;
    }
}
