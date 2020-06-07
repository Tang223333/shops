package com.example.myapplication.model.db;

/**
 * @Author：xiangzai
 * @Date：2020/4/22 20:40
 */
public class AccountTable {

    public static final String TAB_NAME="tab_account";
    public static final String NAME="username";
    public static final String USERNAME="name";
    public static final String ID="id";
    public static final String PHOTO="photo";

    //创建表
    public static final String CREATE_TAB="create table "
            +TAB_NAME+"("
            +ID+" text primary key,"
            +USERNAME +" text,"
            +NAME+" text,"
            +PHOTO+" text);";

}
