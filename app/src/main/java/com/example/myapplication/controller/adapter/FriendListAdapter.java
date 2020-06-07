package com.example.myapplication.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainApplication;
import com.example.myapplication.R;
import com.example.myapplication.model.bean.UserInfo;

import java.util.List;

/**
 * @Author：xiangzai
 * @Date：2020/4/23 21:39
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>{

    private Context context;
    private List<UserInfo> userInfos;


    public FriendListAdapter(List<UserInfo> userInfos) {
        this.context = MainApplication.getContext();
        this.userInfos=userInfos;
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.friend_list_item, null);
        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder holder, int position) {
        FriendListViewHolder friendListViewHolder= (FriendListViewHolder) holder;
        UserInfo userInfo=userInfos.get(position);
        friendListViewHolder.friend_name.setText(userInfo.getId());
    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView friend_name;
        public FriendListViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name=(TextView)itemView.findViewById(R.id.friend_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是否调用这个方法
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(v, userInfos.get(getLayoutPosition()));
                    }
                }
            });
        }
    }


    //----------定义点击监听接口----------------//
    /**
     * 当RecyclerView某个被点击的时候回调
     * view：点击item的视图
     * data：点击得到的数据
     */
    public interface OnItemClickListener{
        void onItemClick(View view , UserInfo data);
    }
    private OnItemClickListener onItemClickListener;

    //设置RecyclerView某条的监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
