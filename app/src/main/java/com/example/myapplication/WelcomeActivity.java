package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.hxim.ECLogin;
import com.example.myapplication.hxim.TestHXActivity;
import com.example.myapplication.model.Model;
import com.example.myapplication.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {

            //判断进入主页还是登陆页面
            toMainOrLogin();
        }
    };

    private void toMainOrLogin() {

        Model.getInstance().getGlobalExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                //当前账号是否已经登陆过
                if(EMClient.getInstance().isLoggedInBefore()){//登陆过
                    //获取用户信息
                    UserInfo account = Model.getInstance().getUserAccountDAO().getAccountById(EMClient.getInstance().getCurrentUser());
                    if(account==null){
                        //跳转到登陆页面
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        //跳转到主页面
                        Log.e("TAG", EMClient.getInstance().isLoggedInBefore()+"");
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    //跳转到登陆也面
                    Log.e("TAG", "NOisLoggedInBefore");
                    Intent intent = new Intent(WelcomeActivity.this, ECLogin.class);
                    startActivity(intent);
                }
                Log.e("TAG", EMClient.getInstance().isLoggedInBefore()+"");
            }
        });
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        handler.sendMessageDelayed(Message.obtain(), 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
