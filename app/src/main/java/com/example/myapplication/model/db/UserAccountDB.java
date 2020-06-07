package com.example.myapplication.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @Author：xiangzai
 * @Date：2020/4/22 20:33
 */
public class UserAccountDB extends SQLiteOpenHelper {
    //构造方法
    public UserAccountDB(@Nullable Context context) {
        super(context,"account.db", null, 1);
    }

    //数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountTable.CREATE_TAB);
    }

    //数据库更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
