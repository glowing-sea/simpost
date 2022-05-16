package com.example.login.Database;

import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.login.DataContainer.Comment;
import com.example.login.DataContainer.Post;

import java.util.ArrayList;
import java.util.HashSet;

public interface UserDAO {

    // ================================ POSTS MANAGEMENT ======================================== //

    boolean addPost (Post post);

    void deletePost (int postID);

    Post getPost (int postID);

    boolean setLikes (int postID, HashSet<String> likes);

    boolean setViews (int postID, HashSet<String> views);

    boolean setComments (int postID, ArrayList<Comment> comments);

    boolean addLikes (int postID, String username);

    boolean addViews (int postID, String username);

    boolean addComments (int postID, Comment comment);


    // ============================ ADDING AND DELETING USERS =================================== //

    // The following methods access, insert, or delete, a whole row in the database.

    // Add a user
    int addUser(String username, String password);

    // Delete a user
    void deleteUser (String username);

    // Delete all users
    void truncateUsers ();

    Cursor getCursor(String[] columns, String tableName);

    // ======================== SETTING AND GETTING A USER'S ATTRIBUTES ========================= //

    // The following methods access and update a single cell in the database.

    // Password
    boolean setPassword(String username, String newPassword);
    String getPassword(String username);

    // Following
    boolean setFollowing(String username, ArrayList<String> following);
    ArrayList<String> getFollowing(String username);

    // Signature
    boolean setSignature(String username, String newSignature);
    String getSignature(String username);

    // Age
    boolean setAge(String username, int age);
    int getAge(String username);

    // Gender
    boolean setGender(String username, int gender);
    int getGender(String username);

    // Location
    boolean setLocation(String username, String location);
    String getLocation(String username);

    // Privacy Setting
    boolean setPrivacySettings(String username, ArrayList<Boolean> settings);
    ArrayList<Boolean> getPrivacySettings(String username);

    // Blacklist
    boolean setBlacklist(String username, ArrayList<String> blacklist);
    ArrayList<String> getBlacklist(String username);

    // View History
    boolean setViewHistory(String username, ArrayList<Integer> historyInt);
    ArrayList<Integer> getViewHistory(String username);

    // Background
    boolean setBackground(String username, Bitmap background);
    Bitmap getBackground(String username);

    // Avatar
    boolean setAvatar(String username, Bitmap avatar);
    Bitmap getAvatar(String username);

    void setMessage(String usname, String mess);
}
