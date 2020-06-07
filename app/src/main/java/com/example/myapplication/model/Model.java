package com.example.myapplication.model;

import android.content.Context;

import com.example.myapplication.model.dao.UserAccountDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据模型层全局类（所有模型层的操作都必须经过此类）
 * @Author：xiangzai
 * @Date：2020/4/22 18:10
 */

public class Model {

    private Context mContext;
    private ExecutorService executorService= Executors.newCachedThreadPool();

    private UserAccountDAO userAccountDao;
    //创建对象
    private static Model model=new Model();

    //私有化构造
    private Model(){}

    //获取单例对象
    public static Model getInstance(){
        return model;
    }

    //初始化方法
    public void initModel(Context context){
        mContext=context;
        userAccountDao=new UserAccountDAO(mContext);

    }

    //获取全局线程池对象
    public ExecutorService getGlobalExecutorService() {
        return executorService;
    }


    //用户登陆成功方法
    public void loginSuccess() {

    }

    //获取用户账号数据库操作类对象
    public UserAccountDAO getUserAccountDAO(){
        return userAccountDao;
    }

}
