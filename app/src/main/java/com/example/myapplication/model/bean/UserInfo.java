package com.example.myapplication.model.bean;

/**
 *
 * 用户bean类
 * @Author：xiangzai
 * @Date：2020/4/22 20:17
 */
public class UserInfo {

    private String name;//名称
    private String id;//用户id
    private String photo;//头像
    private String username;//用户名称

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
        this.id = name;
        this.username = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", photo='" + photo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
