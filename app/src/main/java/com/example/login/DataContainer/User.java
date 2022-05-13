package com.example.login.DataContainer;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    // Authentication
    private String username;
    private String password;

    // Profile
    private Byte[] avatar;
    private Byte[] background;
    private ArrayList<String> following;
    private String info;
    private int age;
    private int gender; // 0: male, 1: female, 2: other

    // Interactions
    private int totalViews;
    private int totalLikes;
    private ArrayList<String> viewHistory;

    // Privacy Setting
    private boolean hideInfo;
    private boolean hideAge;
    private boolean hideGender;
    private boolean hideFollowing;
    private boolean hideAbusiveLanguage;
    private ArrayList<String> blockedUsers;

    // Chat History
    private ArrayList<ArrayList<String>> messageHolder;

    // Preserved Column
    private String preserved;












    public User(String name, String password){
        this.username = name;
        this.password = password;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
