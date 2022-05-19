package com.example.login.DataContainer;

import android.graphics.Bitmap;

import java.util.HashSet;

/*
A local temporary copy of data of a user
This class is mainly used for transferring data from the database to either Me or Other class.
 */
public class User{
    private String username;
    private int age;
    private Gender gender;
    private String location;
    private String signature;
    private Bitmap avatar;
    private Bitmap background;
    private HashSet<String> following;
    private HashSet<String> followers;
    private HashSet<String> blacklist;
    private HashSet<Integer> history;

    /*
    The following attributes of a user are only stored in the database. No local temporary copy.
    String password (for security)
    ArrayList<Boolean> privacy (for security)
    ArrayList<Message> messages (make sure the message box is always up to date)
     */

    public User(String username, int age, Gender gender, String location, String signature,
                Bitmap avatar, Bitmap background, HashSet<String> following,
                HashSet<String> blacklist, HashSet<Integer> history) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.signature = signature;
        this.avatar = avatar;
        this.background = background;
        this.following = following;
        this.blacklist = blacklist;
        this.history = history;
    }

    public User(){};

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getSignature() {
        return signature;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public Bitmap getBackground() {
        return background;
    }

    public HashSet<String> getFollowing() {
        return following;
    }

    public HashSet<String> getBlacklist() {
        return blacklist;
    }

    public HashSet<Integer> getHistory() {
        return history;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }
}


