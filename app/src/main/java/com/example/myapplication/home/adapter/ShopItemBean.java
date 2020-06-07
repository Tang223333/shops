package com.example.myapplication.home.adapter;

import android.widget.ImageView;

class ShopItemBean {
    private int price;
    private String name;
    private ImageView image;

    public ShopItemBean(int price, String name, ImageView image) {
        this.price = price;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShopItemBean{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", image=" + image +
                '}';
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
