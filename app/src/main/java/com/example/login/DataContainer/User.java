package com.example.login.DataContainer;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;
    private int userID;
    public User(String name, String password){
        this.username = name;
        this.password = password;
        this.userID = 123;//这里加上userid的算法
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public int getUserId(){
        return this.userID;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
