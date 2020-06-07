package com.example.myapplication.home.bean;

import android.widget.ImageView;

public class ChannerBean {
    private ImageView Image;
    private String title;

    public ChannerBean(String title) {
 
        this.title = title;
    }

    @Override
    public String toString() {
        return "ChannerBean{" +
                "Image=" + Image +
                ", title='" + title + '\'' +
                '}';
    }

    public ImageView getImage() {
        return Image;
    }

    public void setImage(ImageView image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

