package com.example.myapplication.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainApplication;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;


public class BaseTitleBar extends LinearLayout implements View.OnClickListener {

    private View myself;
    private View search_bar;
    private View email;

    public BaseTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        myself=getChildAt(0);
        search_bar=getChildAt(1);
        email=getChildAt(2);

        search_bar.setOnClickListener(this);
        myself.setOnClickListener(this);
        email.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email_base:
                Toast.makeText(MainApplication.getContext(), "邮箱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myself:

                Toast.makeText(MainApplication.getContext(), "个人", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_base:
                Toast.makeText(MainApplication.getContext(), "搜索 ", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
