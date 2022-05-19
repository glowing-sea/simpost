package com.example.login.DataContainer;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashSet;


public interface UserState {

    /**
     *
     * 14 Setter Method of a user
     *
     */


    String getUsername();

    String getPassword();

    int getAge();

    Gender getGender();

    String getLocation();

    String getSignature();

    Bitmap getAvatar();

    Bitmap getBackground();

    HashSet<String> getFollowing();

    HashSet<String> getBlacklist();

    HashSet<Integer> getHistory();

    HashSet<String> getFollowers();

    ArrayList<Boolean> getPrivacy();

    ArrayList<Message> getMessages();

    /**
     *
     * 12 Setter Method of a user
     *
     */

    boolean setPassword(String password);

    boolean setAge(int age);

    boolean setGender(Gender gender);

    boolean setLocation(String location);

    boolean setSignature(String signature);

    boolean setAvatar(Bitmap avatar);

    boolean setBackground(Bitmap background);

    boolean setFollowing(HashSet<String> following);

    boolean setBlacklist(HashSet<String> blacklist);

    boolean setHistory(HashSet<Integer> history);

    boolean setPrivacy(ArrayList<Boolean> privacy);

    boolean setMessages(ArrayList<Message> messages);
}
