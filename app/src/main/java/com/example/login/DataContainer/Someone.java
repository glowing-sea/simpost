package com.example.login.DataContainer;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashSet;

public class Someone extends User implements Serializable {
    public final String username;
    private final  String signature;
    private final Bitmap avatar;
    private final  Bitmap background;
    private final  HashSet<String> blacklist;

    // The fields below may be null based on the privacy setting of this person
    private final  HashSet<String> following;
    private final  HashSet<String> followers;
    private final  int age;
    private final  Gender gender;
    private final  String location;



    public Someone(String username, HashSet<String> following, HashSet<String> follower,
                   String signature, int age, Gender gender, String location,
                   HashSet<String> blacklist, Bitmap avatar, Bitmap background) {
        this.username = username;
        this.following = following;
        this.followers = follower;
        this.signature = signature;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.blacklist = blacklist;
        this.avatar = avatar;
        this.background = background;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Bitmap getAvatar() {
        return avatar;
    }

    @Override
    public Bitmap getBackground() {
        return background;
    }

    @Override
    public HashSet<String> getBlacklist() {
        return blacklist;
    }

    @Override
    public HashSet<String> getFollowing() {
        return following;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    @Override
    public String getSignature() {
        return signature;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
