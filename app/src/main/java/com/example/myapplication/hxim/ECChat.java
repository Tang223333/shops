package com.example.myapplication.hxim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

/**
 * @Author：xiangzai
 * @Date：2020/4/19 15:34
 */
public class ECChat extends AppCompatActivity implements EMMessageListener {


    private Button send;
    private EditText input;

    private TextView textView;

    private String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        number=intent.getStringExtra("number");


        initView();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=input.getText().toString();

                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, number);
                //如果是群聊，设置chattype，默认是单聊

                message.setChatType(EMMessage.ChatType.Chat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);

                textView.setText(textView.getText().toString()+"\n"+content);

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
        });

    }

    private void initView() {
        send=(Button)findViewById(R.id.btn_send);
        input=(EditText)findViewById(R.id.chat_input);
        textView=(TextView)findViewById(R.id.text_content);
    }





    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for(EMMessage message:list){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(textView.getText()+"\n"
                    +((EMTextMessageBody)message.getBody()).getMessage());
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
        EMClient.getInstance().chatManager().addMessageListener(ECChat.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(ECChat.this);
    }
}
