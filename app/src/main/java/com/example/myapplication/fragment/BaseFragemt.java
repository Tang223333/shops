package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public abstract class BaseFragemt extends Fragment {

    protected View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int rootView=loadView();
        view=inflater.inflate(rootView, container, false);
        return view;

    }
    //强制子类实现该方法，返回布局
    protected abstract int loadView();

    //当孩子需要初始化数据的时候，重写该方法，用于请求数据，或显示数据
    public void initData(){
        //先用着本地数据，后期在搞服务器
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

}
