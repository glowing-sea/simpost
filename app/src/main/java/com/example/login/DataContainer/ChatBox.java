package com.example.login.DataContainer;

import java.io.Serializable;

public class ChatBox implements Serializable {

    private String username;
    private String message;

    public ChatBox(String name, String message){
        this.username = name;
        this.message = message;
    }
    public String getUsername(){
        return this.username;
    }
    public String getmessage(){
        return this.message;
    }
}
