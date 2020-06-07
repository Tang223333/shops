package com.example.myapplication.shop.utils;

import android.content.Context;

import com.example.myapplication.MainApplication;
import com.example.myapplication.shop.bean.CommodityInformation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 商品存储类
 * @Author：xiangzai
 * @Date：2020/4/21 16:06
 */
public class CartStorage {

    private static CartStorage cartStorage;
    private Context context;
    private  List<CommodityInformation> list;

//    //SparseArray性能优于HashMap，内存中存储用SparseArray
//    private SparseArray<CommodityInformation> sparseArray;

    public CartStorage(Context context) {
        this.context = context;
        list=new ArrayList<>();
        initStorage();

    }


    //得到购物车实例
    public static CartStorage getInstance(){
        if(cartStorage==null){
            cartStorage=new CartStorage(MainApplication.getContext());
        }
        return cartStorage;
    }


    private void initStorage() {
        //这个方法是将本地的数据放到购物车列表中，暂时用于测试，到时候将再换成从服务端拿数据
        list.add(new CommodityInformation("服务器图片地址", "商品名称", 20));
        list.add(new CommodityInformation("服务器图片地址", "商品名称", 30));
        list.add(new CommodityInformation("服务器图片地址", "商品名称", 40));
    }




    //获取本地的所有数据
    public List<CommodityInformation> getAllData() {
       return list;
    }

    //添加数据
    public void addData(CommodityInformation data){
        list.add(data);
    }

}
