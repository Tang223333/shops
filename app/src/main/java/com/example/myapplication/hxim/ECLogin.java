package com.example.myapplication.hxim;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Model;
import com.example.myapplication.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


/**
 * 登陆界面
 * @Author：xiangzai
 * @Date：2020/4/19 15:34
 */
public class ECLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText inputUsername;
    private EditText inputPassword;
    private Button login;
    private Button sign;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();



        login.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    private void initView() {
        inputUsername=(EditText)findViewById(R.id.et_username);
        inputPassword=(EditText)findViewById(R.id.st_password);
        login=(Button) findViewById(R.id.btn_login);
        sign=(Button) findViewById(R.id.btn_sign);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_sign:
                regist();
                break;
        }
    }


    //用户登陆逻辑处理
    private void login(){
        //1，获取输入用户名和密码
        String username = inputUsername.getText().toString();
        String password=inputPassword.getText().toString();
        //2，校验输入的用户名和密码
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(ECLogin.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }else{
            signIn(username,password);
        }
    }


    //注册逻辑处理
    private void regist(){
        String registlUsername = inputUsername.getText().toString();
        String registPassword=inputPassword.getText().toString();
        if(TextUtils.isEmpty(registlUsername)||TextUtils.isEmpty(registPassword)){
            Toast.makeText(ECLogin.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }else{
            signUp(registlUsername,registPassword);
        }
    }






    //注册方法
    private void signUp(String inputUsername, String inputPassword){

        new Thread(){
            @Override
            public void run() {
                try {
                    //注册用户名和密码
                    EMClient.getInstance().createAccount(inputUsername,inputPassword );//同步方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ECLogin.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    Log.e("TAG", "注册失败"+e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ECLogin.this, "注册用户已存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }.start();
    }




    //登陆方法
    private void signIn(String username, String password) {
        if ("".equals(inputUsername.getText().toString())) {
            Log.e("main", "输入为空");
        } else {
            EMClient.getInstance().login(username, password, new EMCallBack() {//回调
                @Override
                //登陆成功处理
                public void onSuccess() {
                    //模型层处理
                    Model.getInstance().loginSuccess();
                    //保存用户信息到本地数据库
                    Model.getInstance().getUserAccountDAO().addAccount(new UserInfo(username));

                    //提示登陆成功,跳转到主页面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ECLogin.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ECLogin.this, MainActivity.class));
                            Log.e("main", "登陆成功！");
                        }
                    });
                    finish();
                }
                //登陆过程中处理
                @Override
                public void onProgress(int progress, String status) {

                }

                //登陆失败处理
                @Override
                public void onError(int code, String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ECLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }


}
