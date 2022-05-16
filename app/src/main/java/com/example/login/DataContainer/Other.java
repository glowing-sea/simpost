package com.example.login.DataContainer;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class Other {
    public String username;
    public Context context;


    // May not be initialised
    ArrayList<String> following;
    ArrayList<String> follower;
    String signature;
    int age;
    int gender;
    String location;
    ArrayList<Boolean> privacy;
    ArrayList<String> blacklist;
    Bitmap avatar;
    Bitmap background;
    //

    public Other (String username, Context context){
        this.username = username;
        this.context = context;
    }
    public ArrayList<String> getFollower(){
        return this.follower;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public String getSignature() {
        return signature;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Boolean> getPrivacy() {
        return privacy;
    }

    public ArrayList<String> getBlacklist() {
        return blacklist;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public Bitmap getBackground() {
        return background;
    }
}
