package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragment.BaseFragemt;
import com.example.myapplication.fragment.FriendFragemnt;
import com.example.myapplication.fragment.HomeFragemnt;
import com.example.myapplication.fragment.SearchFragemnt;
import com.example.myapplication.fragment.ShopFragemnt;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_navigation)
    BottomNavigationView navigationView;

    private HomeFragemnt homeFragemnt;
    private FriendFragemnt friendFragemnt;
//    private SearchFragemnt searchFragemnt;
    private ShopFragemnt shopFragemnt;
    private Fragment tempFragment;
    private FragmentManager fragmentManager;
    private List<BaseFragemt> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//

        ButterKnife.bind(this);

        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments=new ArrayList<>();
        homeFragemnt=new HomeFragemnt();
        friendFragemnt=new FriendFragemnt();
//        searchFragemnt=new SearchFragemnt();
        shopFragemnt=new ShopFragemnt();
        fragmentManager=getSupportFragmentManager();
        fragments.add(homeFragemnt);
        fragments.add(friendFragemnt);
        fragments.add(shopFragemnt);
//        fragments.add(searchFragemnt);

        switchFragment(tempFragment,homeFragemnt);
    }

    private int position=0;

    private void initListener() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Log.e("TAG", "首页");
                        position = 0;
                        break;
                    case R.id.friend:
                        Log.e("TAG", "好友");
                        position = 1;
                        break;
                    case R.id.shop:
                        Log.e("TAG", "商城");

                        position = 2;
                        break;
//                    case R.id.search:
//                        Log.e("TAG", "搜索");
//                        position = 3;
//                        break;
                    default:
                        position = 0;
                }
                BaseFragemt baseFragemt=getFragment(position);

                switchFragment(tempFragment,baseFragemt);
                return true;
            }
        });
    }

    private BaseFragemt getFragment(int position) {

        if(fragments!=null&&fragments.size()>0){
            BaseFragemt baseFragemt=fragments.get(position);
            return baseFragemt;
        }
        return null;
    }




    private void switchFragment(Fragment fromFragment, BaseFragemt nextFragemt) {
        if(tempFragment!=nextFragemt){
            tempFragment=nextFragemt;
            if(nextFragemt!=null){
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                if(!nextFragemt.isAdded()){
                    if(fromFragment!=null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.main_page_container, nextFragemt).commit();
                }else {
                    if(fromFragment!=null){
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragemt).commit();
                }

            }
        }

    }

}
