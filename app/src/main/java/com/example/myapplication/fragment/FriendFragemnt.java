package com.example.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainApplication;
import com.example.myapplication.R;
import com.example.myapplication.WelcomeActivity;
import com.example.myapplication.controller.ChatActivity;
import com.example.myapplication.controller.adapter.FriendListAdapter;
import com.example.myapplication.hxim.TestHXActivity;
import com.example.myapplication.model.Model;
import com.example.myapplication.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.w3c.dom.Text;

import java.util.List;

public class FriendFragemnt extends BaseFragemt implements View.OnClickListener {


    private RecyclerView friend_list;
    //    private EditText search_friend;
//    private Button btn_search;
    private Button return_friend;
    private Button btn_accept_friend;
    private Button btn_sign_out;
    private EditText et_accept_friend;
    private FriendListAdapter adapter;
    private String currentUser;


    @Override
    protected int loadView() {
        return R.layout.fragment_friend;
    }



    @Override
    public void initData() {
        super.initData();

    }

    private void initLayout() {
        friend_list=(RecyclerView)view.findViewById(R.id.friend_list);
//        search_friend=(EditText)findViewById(R.id.search_friend);
//        btn_search=(Button)findViewById(R.id.btn_search);
        et_accept_friend=(EditText) view.findViewById(R.id.et_accept_friend);
        btn_accept_friend=(Button)view.findViewById(R.id.btn_accept_friend);
        btn_sign_out=(Button)view.findViewById(R.id.btn_sign_out);
        return_friend=(Button)view.findViewById(R.id.return_friend);
        btn_accept_friend.setOnClickListener(this);
        btn_sign_out.setOnClickListener(this);
        return_friend.setOnClickListener(this);
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                find();
//            }
//        });

    }


    private void initListener() {
        //设置监听。本身是没有这个方法。是自己在适配器内里面自己定义的
        adapter.setOnItemClickListener(new FriendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UserInfo data) {
                Log.e("TAG", ""+data.getId());
                Intent intent=new Intent(getContext(), ChatActivity.class);
                intent.putExtra("number", data.getId());
                startActivity(intent);
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
                    Log.e("TAG", ""+ids);
                    List<UserInfo> contactsById = Model.getInstance().getUserAccountDAO().getContactsById(ids);
                    for(UserInfo userInfo:contactsById){
                        if(userInfo==null) {
                            return;
                        }
                    }
                    Log.e("TAG", ""+contactsById);
                    getActivity().runOnUiThread(new Runnable(){
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
        currentUser = EMClient.getInstance().getCurrentUser();
        String number=et_accept_friend.getText().toString();
        UserInfo accountById = Model.getInstance().getUserAccountDAO().getAccountById(number);
        Log.e("TAG", ""+accountById);
        if(TextUtils.isEmpty(number)){
            Toast.makeText(getContext(), "输入号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(accountById==null){
            Toast.makeText(getContext(), "发起聊天的对象不存在", Toast.LENGTH_SHORT).show();
            return;
        }else if(currentUser.equals(number)){

            Toast.makeText(getContext(), "跟自己聊天，这么无聊码？", Toast.LENGTH_SHORT).show();
            return;
        }
        Model.getInstance().getGlobalExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    EMClient.getInstance().contactManager().acceptInvitation(number);
                    Log.e("TAG", "添加成功");
                    returnFriendList();

                    //进入聊天界面

                    Intent intent=new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("number", et_accept_friend.getText().toString());
                    startActivity(intent);
                    et_accept_friend.setText("");

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("TAG", "添加失败");

                }
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("TAG", "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        initLayout();
        returnFriendList();
        Log.e("TAG", "onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG", "onHiddenChanged");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_accept_friend:
                Log.e("TAG", "btn_accept_friend");
                acceptFriend();
                break;
            case R.id.return_friend:
                Log.e("TAG", "btn_accept_friend");
                returnFriendList();
                break;
            case R.id.btn_sign_out:
                Log.e("TAG", "btn_accept_friend");
                new Thread(){
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                //如果这里的页面不是activity而是fragment，则先getActivity就有runOnUiThread了
                                //退出成功
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getActivity(), WelcomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                            }
                            @Override
                            public void onError(int i, String s) {
                                //退出失败
                                Log.e("TAG", "退出失败");
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

}
