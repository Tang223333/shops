package com.example.myapplication.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.shop.bean.CommodityInformation;
import com.example.myapplication.shop.view.AddSubView;


import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {



    //购物车的商品信息
    private List<CommodityInformation> showCart;
    private Context context;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;

    private boolean isSelectedAll;//删除框全选按钮状态

    //将点击全选和价格传进来
    public ShoppingCartAdapter(final List<CommodityInformation> showCart, Context context, CheckBox checkboxAll, TextView tvShopcartTotal) {
        this.showCart = showCart;
        this.context = context;
        this.checkboxAll=checkboxAll;
        this.tvShopcartTotal=tvShopcartTotal;


        //刚开始进来，应该计算有打勾商品的总价
        showShowTotalPrice();
        //刚进来时校验商品是否为全选
        checkAll();

        //设置点击事件
        setListener();
    }


    //设置item的监听
    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //得到当前item对象
                CommodityInformation information=showCart.get(position);
                //设置取反状态
                information.setSelected(!information.isSelected());
                //刷新
                notifyItemChanged(position);
                //检验是否全选
                checkAll();
                //刷新总价格
                showShowTotalPrice();
            }
        });

        //设置checkAll点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<showCart.size();i++){
                    //得到状态
                    boolean isCheckBox=checkboxAll.isChecked();

                    //根据checkbox的状态来设置其他item的状态
                    checkAll_none(isCheckBox);
                    //上面和下面任选一，效率与简洁的选择
//                    CommodityInformation information=showCart.get(i);
//                    if(isCheckBox){
//                        information.setSelected(true);
//                    }else{
//                        information.setSelected(false);
//                    }
//                    notifyItemChanged(i);;

                    showShowTotalPrice();
                }
            }
        });

    }

    public void checkAll_none(boolean isCheckBox) {
        for (int i=0;i<showCart.size();i++){
            CommodityInformation information=showCart.get(i);
            information.setSelected(isCheckBox);
            notifyItemChanged(i);
        }
    }

    //校验是否全选
    public void checkAll() {
//        Log.e("TAG", "checkAll");
        if (showCart!=null&&showCart.size()>0){
            //用于记录选中数量
            int number=0;
            for(int i=0;i<showCart.size();i++){
                CommodityInformation information=showCart.get(i);
                if(!information.isSelected()){
                    checkboxAll.setChecked(false);
                }else{
                    number++;
                }
            }
            if(number==showCart.size()){
                checkboxAll.setChecked(true);
            }

        }else{
            checkboxAll.setChecked(false);
        }
    }


    public void showShowTotalPrice() {
        tvShopcartTotal.setText("合计："+getTotal());
    }

    //计算购物车总价
    public double getTotal() {
        double totalPrice=0.0;
        if(showCart!=null&&showCart.size()>0){
            for(int i=0;i<showCart.size();i++){
                CommodityInformation information=showCart.get(i);
                //判断是否被选中
                if(information.isSelected()) {
                    int price = information.getPrice();
                    //计算总价和。将数量转换成double类型
                    totalPrice = totalPrice+price*information.getNumber();
                }
            }
        }
        return totalPrice;
    }

    //之后写服务端用Glide请求数据，暂时先用一些固定数据测，以后再写别的
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //根据位置得到对应的bean对象
        final CommodityInformation information=showCart.get(position);
        //得到商品是否是点击状态
        holder.checkbox_show.setChecked(information.isSelected());
        //得到商品的数量，并设置到数量框里面
        holder.addSubView.setValue(information.getNumber());
        holder.showcart_price.setText("$:"+information.getPrice());

        //设置监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int value) {//当点击增加或减少按钮时，回调，返回更新后的数量
                //更新列表里面的数量
                information.setNumber(value);
                //本地更新，更新服务器的数据；还没写服务器，不用写着一步先
                //刷新适配器
                notifyItemChanged(position);
                //重新计算价格总和
                showShowTotalPrice();
            }
        });
    }

    //移除选中的item
    public void deleteData() {
        if(showCart!=null&&showCart.size()>0){
            for(int i=0;i<showCart.size();i++){
                CommodityInformation information=showCart.get(i);
                if(information.isSelected()){
                    //内存中移除
                    showCart.remove(information);
                    //从服务器中移除（暂时不写，还没写服务器）
                    //刷新
                    notifyItemRemoved(i);
                    //每一除一条表示，下面一条会补充到这个位置
                    i--;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.showcart_item,null);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return showCart.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkbox_show;
        private ImageView showcart_image;
        private TextView showcart_name;
        private TextView showcart_price;
        private AddSubView addSubView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            checkbox_show=(CheckBox) itemView.findViewById(R.id.checkbox_show);
            showcart_image=(ImageView)itemView.findViewById(R.id.show_cart_image);
            showcart_name=(TextView)itemView.findViewById(R.id.showcart_name);
            showcart_price=(TextView)itemView.findViewById(R.id.showcart_price);
            addSubView=(AddSubView)itemView.findViewById(R.id.addSubView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }



    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
