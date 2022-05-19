package com.example.login.DataContainer;

import android.graphics.Bitmap;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Someone extends User implements Serializable, UserState {
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


    /**
     *
     * The following 4 getting method are not available when a user is in logout state.
     *
     */

    @Override
    // Someone's privacy setting is not available
    public ArrayList<Boolean> getPrivacy() {
        return null;
    }

    @Override
    public ArrayList<Message> getMessages() {
        return null;
    }

    @Override
    // Getting password is not available in logout state
    public String getPassword() {
        return null;
    }

    @Override
    // Someone history is not visiable
    public HashSet<Integer> getHistory() {
        return null;
    }

    /**
     *
     * All 12 setting methods are not available when a user is in logout state.
     *
     */

    // Password
    public boolean setPassword(String password){
        return false;
    }

    // Location
    public boolean setLocation(String location){
        return false;
    }


    // Age
    public boolean setAge(int age){
        return false;
    }

    // Gender
    public boolean setGender(Gender gender){
        return false;
    }


    // Signature
    public boolean setSignature(String newSignature){
        return false;
    }

    // Avatar
    public boolean setAvatar(Bitmap avatar){
        return false;
    }

    // Background (Be Careful of Null return!)
    public boolean setBackground(Bitmap background){
        return false;
    }

    // Following
    public boolean setFollowing(HashSet<String> following){
        return false;
    }

    // Blacklist
    public boolean setBlacklist(HashSet<String> blacklist){
        return false;
    }

    // History
    public boolean setHistory(HashSet<Integer> history){
        return false;
    }

    // Privacy
    public boolean setPrivacy(ArrayList<Boolean> history){
        return false;
    }

    // Messages
    public boolean setMessages(ArrayList<Message> messages){
        return false;
    }
}
