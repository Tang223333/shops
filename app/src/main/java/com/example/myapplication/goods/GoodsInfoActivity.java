package com.example.myapplication.goods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.shop.bean.CommodityInformation;
import com.example.myapplication.shop.utils.CartStorage;


public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView shop_price;
    private Button shop_collection;
    private ImageView return_home;
    private Intent intent;
    private CommodityInformation shop_item;
    private String good_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        initView();
    }

    private void initView() {
        shop_price=(TextView)findViewById(R.id.shop_price);
        shop_collection=(Button)findViewById(R.id.shop_collection);
        return_home=(ImageView)findViewById(R.id.return_home);
        intent=getIntent();
        good_value = intent.getStringExtra("GOOD_VALUE");

        shop_price.setText("商品价格￥："+good_value);

        return_home.setOnClickListener(this);
        shop_collection.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_collection:
                Log.e("TAG", "首页商品--点击加入购物车");
                shop_item=new CommodityInformation("服务器图片地址", "商品名称", Integer.parseInt(good_value));
                CartStorage.getInstance().addData(shop_item);
                break;
            case R.id.return_home:
                Log.e("TAG", "首页商品--点击返回");
                break;
        }
    }
}
