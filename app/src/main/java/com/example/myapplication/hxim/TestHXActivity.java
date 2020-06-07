package com.example.myapplication.hxim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainApplication;
import com.example.myapplication.R;
import com.example.myapplication.WelcomeActivity;
import com.example.myapplication.controller.adapter.FriendListAdapter;
import com.example.myapplication.model.Model;
import com.example.myapplication.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

import java.util.List;

public class TestHXActivity extends AppCompatActivity implements View.OnClickListener {





















    //------------------------------------第二块代码---------------------------------

    private EditText editText;
    private Button btn;
    private Button btn2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hx);

        initView();
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);

        //第二块初始化布局
        intoTwoView();

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    private void initView() {
        btn=(Button)findViewById(R.id.btn_main);
        btn2=(Button)findViewById(R.id.main_sign_out);
        editText=(EditText)findViewById(R.id.main_username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main://发起聊天按钮
                Intent intent=new Intent(this, ECChat.class);
                intent.putExtra("number", editText.getText().toString());
                Log.e("TAG", "发起聊天");
                startActivity(intent);
                break;
            case R.id.main_sign_out://退出按钮
                new Thread(){
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {

                                //如果这里的页面不是activity而是fragment，则先getActivity就有runOnUiThread了
                                //退出成功
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TestHXActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(TestHXActivity.this, WelcomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {
                                //退出失败
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                //正在退出中
                            }
                        });

                    }

                }.start();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    //进行登陆状态监听
    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Log.e("TAG", "显示帐号在其他设备登录");
                    } else {
                        if (NetUtils.hasNetwork(TestHXActivity.this)) {
                            //连接不到聊天服务器
                            Log.e("TAG", "连接不到聊天服务器");
                        }else{
                            //当前网络不可用，请检查网络设置
                            Log.e("TAG", "当前网络不可用，请检查网络设置");
                        }
                    }
                }
            });
        }
    }









    //------------------------------------第三页面------------------------------------------
    //联系人页面


    private RecyclerView friend_list;
//    private EditText search_friend;
//    private Button btn_search;
    private Button return_friend;
    private Button btn_accept_friend;
    private EditText et_accept_friend;
    private FriendListAdapter adapter;

    private void intoTwoView(){
        friend_list=(RecyclerView) findViewById(R.id.friend_list);
//        search_friend=(EditText)findViewById(R.id.search_friend);
//        btn_search=(Button)findViewById(R.id.btn_search);
        et_accept_friend=(EditText) findViewById(R.id.et_accept_friend);
        btn_accept_friend=(Button)findViewById(R.id.btn_accept_friend);

        return_friend=(Button)findViewById(R.id.return_friend);

        return_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnFriendList();
            }
        });
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                find();
//            }
//        });
        btn_accept_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptFriend();
            }
        });



    }

    private void initListener() {
        //设置监听。本身是没有这个方法。是自己在适配器内里面自己定义的
        adapter.setOnItemClickListener(new FriendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UserInfo data) {
                Log.e("TAG", ""+data.getId());
            }
        });
    }

//
//    //    点击查找按钮的处理，如果查询得到则直接发出添加请求，如果没有该用户则，返回提示
//    private void find(){
//        //获取输入的用户名称
//        String client_number = search_friend.getText().toString();
//        //校验名称
//        if(TextUtils.isEmpty(client_number)){
//            Toast.makeText(this,"输入号码不能为空",Toast.LENGTH_SHORT).show();
//        }else {
//            //判断用户是否存在
//            //参数为要添加的好友的username和添加理由
//            Model.getInstance().getGlobalExecutorService().execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        EMClient.getInstance().contactManager().addContact(client_number, "我想加你好友");
//                        Log.e("TAG", "我想加你好友");
//                    } catch (HyphenateException e) {
//                        Log.e("TAG", "添加好友失败"+e);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(TestHXActivity.this,"添加好友失败",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }



    //从环信返回好友列表
    public void returnFriendList(){
        Model.getInstance().getGlobalExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取好友的id列表
                    List<String> ids = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    List<UserInfo> contactsById = Model.getInstance().getUserAccountDAO().getContactsById(ids);
                    Log.e("TAG", ""+contactsById);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new FriendListAdapter(contactsById);
                            friend_list.setAdapter(adapter);
                            friend_list.setLayoutManager(new LinearLayoutManager(MainApplication.getContext(), LinearLayoutManager.VERTICAL, false));
                            initListener();
                        }
                    });
                    Log.e("TAG", ""+ids);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //环信服务器：接受好友（由于没有服务器，也懒得建表，这里先做一个直接发起聊天的效果）
    public void acceptFriend(){
        Model.getInstance().getGlobalExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                String number=et_accept_friend.getText().toString();
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(number);
                    Log.e("TAG", "添加成功");
                    returnFriendList();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("TAG", "添加失败");

                }
            }
        });
    }






}