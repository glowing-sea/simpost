package com.example.login.DataContainer;

public class User {
    String username;
    String password;
    final int userID;
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
