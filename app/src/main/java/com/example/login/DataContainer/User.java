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
    private String signature;
    private int age;
    private int gender; // 0: male, 1: female, 2: other

    // Interactions
    private int totalViews;
    private int totalLikes;


    private ArrayList<Integer> viewHistory;

    // Privacy Setting
    private ArrayList<Boolean> privacySetting;
//    private boolean hideInfo;
//    private boolean hideAge;
//    private boolean hideGender;
//    private boolean hideFollowing;
//    private boolean hideAbusiveLanguage;


    // Chat History
    private ArrayList<ArrayList<String>> messageHolder;

    // Preserved Column
    private String preserved;



    public Byte[] getAvatar() { return avatar; }
    public Byte[] getBackground() { return background; }
    public ArrayList<String> getFollowing() { return following; }
    public int getAge() { return age; }
    public int getGender() { return gender; }
    public int getTotalViews() { return totalViews; }
    public int getTotalLikes() { return totalLikes; }

    public ArrayList<ArrayList<String>> getMessageHolder() { return messageHolder; }
    public String getPreserved() { return preserved; }


    public void setUsername(String username) { this.username = username; }
    public void setAvatar(Byte[] avatar) { this.avatar = avatar; }
    public void setBackground(Byte[] background) { this.background = background; }
    public void setFollowing(ArrayList<String> following) { this.following = following; }
    public void setAge(int age) { this.age = age; }
    public void setGender(int gender) { this.gender = gender; }
    public void setTotalViews(int totalViews) { this.totalViews = totalViews; }
    public void setTotalLikes(int totalLikes) { this.totalLikes = totalLikes; }


    public void setMessageHolder(ArrayList<ArrayList<String>> messageHolder) { this.messageHolder = messageHolder; }
    public void setPreserved(String preserved) { this.preserved = preserved; }









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
