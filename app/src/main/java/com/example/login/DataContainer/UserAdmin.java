package com.example.login.DataContainer;

import java.io.Serializable;
import java.util.ArrayList;

/*
A user class for admin which contain username and password only.
 */
public class UserAdmin implements Serializable {

    // Authentication
    private String username;
    private String password;

    public UserAdmin(String name, String password){
        this.username = name;
        this.password = password;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password){
        this.password = password;
    }
}
