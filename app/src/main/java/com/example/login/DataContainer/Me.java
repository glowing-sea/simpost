package com.example.login.DataContainer;

import android.content.Context;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;

import java.util.ArrayList;


public class Me {

    // ========================== FIELD SETTING AND GETTING METHODS ============================= //

    private static Me instance = null;
    public String username;
    public Context context;

    private Me() {
    }

    public static Me getInstance() {
        if (instance == null) {
            instance = new Me();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUsername() {
        return username;
    }


    // ========================== USER DATABASE ACCESS METHODS ================================== //

    // Password
    public boolean setPassword(String newPassword) {
        UserDAO db = new UserDAOImpl(context);
        return db.setPassword(username, newPassword);
    }
    public String getPassword() {
        UserDAO db = new UserDAOImpl(context);
        return db.getPassword(username);
    }

    // Following
    public boolean setFollowing(ArrayList<String> following){
        UserDAO db = new UserDAOImpl(context);
        return db.setFollowing(username, following);
    }
    public ArrayList<String> getFollowing() {
        UserDAO db = new UserDAOImpl(context);
        return db.getFollowing(username);
    }

    // Signature
    public boolean setSignature(String newSignature){
        UserDAO db = new UserDAOImpl(context);
        return db.setSignature(username, newSignature);
    }
    public String getSignature() {
        UserDAO db = new UserDAOImpl(context);
        return db.getSignature(username);
    }

    // Age
    public boolean setAge(int age){
        UserDAO db = new UserDAOImpl(context);
        return db.setAge(username, age);
    }
    public int getAge() {
        UserDAO db = new UserDAOImpl(context);
        return db.getAge(username);
    }

    // Gender
    public boolean setGender(int gender){
        UserDAO db = new UserDAOImpl(context);
        return db.setGender(username, gender);
    }
    public int getGender() {
        UserDAO db = new UserDAOImpl(context);
        return db.getGender(username);
    }

    // Total View
    public boolean incrementViews(){
        UserDAO db = new UserDAOImpl(context);
        int currentViews = db.getViews(username);
        currentViews++;
        return db.setViews(username, currentViews);
    }
    public int getViews() {
        UserDAO db = new UserDAOImpl(context);
        return db.getViews(username);
    }

    // Total Like
    public boolean incrementLikes(){
        UserDAO db = new UserDAOImpl(context);
        int currentLikes = db.getLikes(username);
        currentLikes++;
        return db.setLikes(username, currentLikes);
    }
    public int getLike() {
        UserDAO db = new UserDAOImpl(context);
        return db.getLikes(username);
    }

    // Privacy Setting
    public boolean setPrivacySettings(ArrayList<Boolean> settings){
        UserDAO db = new UserDAOImpl(context);
        return db.setPrivacySettings(username, settings);
    }
    public ArrayList<Boolean> getPrivacySettings(){
        UserDAO db = new UserDAOImpl(context);
        return db.getPrivacySettings(username);
    }

    // Blacklist
    public boolean setBlacklist(ArrayList<String> blacklist){
        UserDAO db = new UserDAOImpl(context);
        return db.setBlacklist(username, blacklist);
    }
    public ArrayList<String> getBlacklist(){
        UserDAO db = new UserDAOImpl(context);
        return db.getBlacklist(username);
    }

    // View History
    public boolean setViewHistory(ArrayList<Integer> historyInt){
        UserDAO db = new UserDAOImpl(context);
        return db.setViewHistory(username, historyInt);
    }
    public ArrayList<Integer> getViewHistory(){
        UserDAO db = new UserDAOImpl(context);
        return db.getViewHistory(username);
    }

    // Preserved
    public boolean setPreserved(String newPreserved){
        UserDAO db = new UserDAOImpl(context);
        return db.setPreserved(username, newPreserved);
    }
    public String getPreserved() {
        UserDAO db = new UserDAOImpl(context);
        return db.getPreserved(username);
    }
}



