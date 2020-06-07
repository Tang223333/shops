package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.hxim.ECChat;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener ,EMMessageListener {

    private Button sendMessage;
    private EditText inputMessgae;

    private TextView chatMessage;

    private String number;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        number=intent.getStringExtra("number");
        currentUser =  EMClient.getInstance().getCurrentUser();

        //初始化布局
        initView();
        //初始化监听
        initListener();
    }

    private void initListener() {
        sendMessage.setOnClickListener(this);
    }

    private void initView() {
        sendMessage=(Button)findViewById(R.id.send_message);
        inputMessgae=(EditText)findViewById(R.id.input_message);
        chatMessage=(TextView)findViewById(R.id.chat_message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_message:
                clickSendMessage();
                break;
        }
    }


    private void clickSendMessage(){
        String content=inputMessgae.getText().toString();

        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, number);
        //如果是群聊，设置chattype，默认是单聊

        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        chatMessage.setText(chatMessage.getText().toString()+"\n"+currentUser+"："+content);


        message.setMessageStatusCallback(new EMCallBack(){
            @Override
            public void onSuccess() {
                Log.e("TAG", "发送成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("TAG", "发送失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }



    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for(EMMessage message:list){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatMessage.setText(chatMessage.getText()+"\n"+
                            number+" : "+((EMTextMessageBody)message.getBody()).getMessage());
                }
            });
        }

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }
}
