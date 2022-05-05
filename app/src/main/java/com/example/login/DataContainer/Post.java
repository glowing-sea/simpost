package com.example.login.DataContainer;

import com.google.gson.Gson;

import java.io.File;

public class Post {
    String title;
    String content;
    int postID;
    public Post(String title, String content){
        this.title = title;
        this.content = content;
        this.postID = 123;//加入id的算法
    }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public int getPostID(){
        return this.postID;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
