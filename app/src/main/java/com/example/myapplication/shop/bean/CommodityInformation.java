package com.example.myapplication.shop.bean;

/**
 * 购物车的每一单位的bean类
 */

public class CommodityInformation {

    private String imagePath;
    private String commodityTitle;
    private int price;
    private int number=1;
    private int SHOP_CODE;

    private boolean isSelected=true;



    public CommodityInformation(String imagePath, String commodityTitle, int price) {
        this.imagePath = imagePath;
        this.commodityTitle = commodityTitle;
        this.price = price;
        SHOP_CODE=price;
    }

    public int getSHOP_CODE() {
        return SHOP_CODE;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CommodityInformation{" +
                "imagePath='" + imagePath + '\'' +
                ", commodityTitle='" + commodityTitle + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", SHOP_CODE=" + SHOP_CODE +
                ", isSelected=" + isSelected +
                '}';
    }
}



