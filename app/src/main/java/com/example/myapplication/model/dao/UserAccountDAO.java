package com.example.myapplication.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.model.bean.UserInfo;
import com.example.myapplication.model.db.AccountTable;
import com.example.myapplication.model.db.UserAccountDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户数据库的操作类
 * @Author：xiangzai
 * @Date：2020/4/22 21:03
 */
public class UserAccountDAO {

    private final UserAccountDB userHelper;


    public UserAccountDAO(Context context) {
        userHelper = new UserAccountDB(context);
    }

    //添加用户到数据库
    public void addAccount(UserInfo userInfo){
        //获取数据库对象
        SQLiteDatabase database = userHelper.getReadableDatabase();

        //执行添加操作
        ContentValues values=new ContentValues();
        values.put(AccountTable.USERNAME, userInfo.getUsername());
        values.put(AccountTable.ID, userInfo.getId());
        values.put(AccountTable.NAME, userInfo.getName());
        values.put(AccountTable.PHOTO, userInfo.getPhoto());

        database.replace(AccountTable.TAB_NAME, null, values);
    }

    //根据id获取用户的所有信息
    public UserInfo getAccountById(String id){
        //获取数据库对象
        SQLiteDatabase database = userHelper.getReadableDatabase();

        //执行查询语句
        String sql="select * from "+AccountTable.TAB_NAME+" where "+AccountTable.ID+" =?";
        Cursor cursor = database.rawQuery(sql, new String[]{id});
        UserInfo userInfo=null;
        if(cursor.moveToNext()){
            userInfo=new UserInfo();
            //封装对象
            userInfo.setId(cursor.getString(cursor.getColumnIndex(AccountTable.ID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(AccountTable.NAME)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.USERNAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.PHOTO)));
        }
        //关闭资源
        cursor.close();
        //返回数据
        return userInfo;
    }


    //通过id获取好友列表
    public List<UserInfo> getContactsById(List<String> ids){
        if(ids==null||ids.size()<0){
            return null;
        }else{
            List<UserInfo> contacts= new ArrayList<>();
            for(String id:ids){
                UserInfo contact = getAccountById(id);
                contacts.add(contact);
            }
            return contacts;
        }
    }


}
