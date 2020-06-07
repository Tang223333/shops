package com.example.myapplication.fragment;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.shop.adapter.ShoppingCartAdapter;
import com.example.myapplication.shop.bean.CommodityInformation;
import com.example.myapplication.shop.utils.CartStorage;

import java.util.List;

public class ShopFragemnt extends BaseFragemt implements View.OnClickListener {

    private static int ACTION_EDIT=1;
    private static int ACTION_SECCUSE=2;

    private RecyclerView recyclerview;
    private LinearLayout ll_edit;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private TextView showCartEdit;
    private Button btnCollection;
    private Button btnDelete;
    public ShoppingCartAdapter adapter;
    private RelativeLayout rl_deltet;
    private List<CommodityInformation> shopCart;



    @Override
    protected int loadView() {

        return R.layout.fragment_shop;
    }


    public View initView() {
        recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
        ll_edit = (LinearLayout)view.findViewById( R.id.ll_edit );
        checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
        tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
        btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
        showCartEdit=(TextView)view.findViewById(R.id.showcart_edit);
        btnCollection=(Button)view.findViewById(R.id.btn_collection);
        btnDelete=(Button)view.findViewById(R.id.btn_delete);
        rl_deltet=(RelativeLayout)view.findViewById(R.id.rl_delete);


        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection .setOnClickListener(this);
        //设置编辑点击
        initDelete();

        return view;
    }


    //点击编程时运行的方法
    private void initDelete() {

        showCartEdit.setTag(ACTION_EDIT);
        showCartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action= (int) v.getTag();
                if(action==ACTION_EDIT){
                    //显示删除和收藏
                    showDelete();
                }else{
                    //隐藏删除和收藏
                    hideDelete();
                    adapter.showShowTotalPrice();
                }
            }
        });
    }

    private void hideDelete() {
        showCartEdit.setTag(ACTION_EDIT);
        showCartEdit.setText("编辑");
        //检测是否全勾选
        adapter.checkAll();
        rl_deltet.setVisibility(View.GONE);
        ll_edit.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
        //设置文本
        showCartEdit.setTag(ACTION_SECCUSE);
        showCartEdit.setText("完成");
        //检测是否全勾选
        adapter.checkAll();
        //显示删除栏
        rl_deltet.setVisibility(View.VISIBLE);
        //隐藏结算栏
        ll_edit.setVisibility(View.GONE);
    }



    //得到此类时加载资源
    @Override
    public void initData() {
        super.initData();
        initView();
        showData();
    }

    //获取购物车的数据
    public void showData() {
        //得到购物车列表
        shopCart=getAllData();
        //如果购物车列表不为null，则显示数据....
        if(shopCart!=null) {
            adapter=new ShoppingCartAdapter(shopCart,getContext(),checkboxAll,tvShopcartTotal);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        }else{
            //如果没有数据则显示空布局（还没建）
        }

    }

    //这个方法是将本地的数据放到购物车列表中，暂时用于测试，到时候将再换成从服务端拿数据
    private List<CommodityInformation> getAllData() {
        List<CommodityInformation> list = CartStorage.getInstance().getAllData();
        Log.e("TAG", "------"+list);
        return list;
    }


    @Override
    public void onClick(View v) {
        if ( v == btnCheckOut ) {

        }else if(v==btnCollection){

        }else  if(v==btnDelete){
            //删除选择中item
            adapter.deleteData();
            //校验状态
            adapter.checkAll();
        }
    }

    private boolean isHide=true;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHide) {
            isHide=false;
        }else{
            showData();
            isHide=true;
        }
    }
}
