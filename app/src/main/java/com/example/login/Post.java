package com.example.login;

public class Post {
    String title;
    String content;
    int postID;
    public Post(String title, String content, int id){
        this.title = title;
        this.content = content;
        this.postID = id;
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
}
