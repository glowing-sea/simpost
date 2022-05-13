package com.example.login.Database;

import android.database.Cursor;

import java.util.ArrayList;

public interface UserDAO {
    // Admin
    boolean addUser(String username, String password);
    Cursor readAllData();
    Boolean loginAuthentication(String loginUsername, String loginPassword);
    void deleteUser(String username);
    void truncateUsers();


    // Password
    String getPassword(String username);
    boolean setPassword(String username, String newPassword);

    // Following
    ArrayList<String> getFollowing(String username);
    boolean setFollowing(String username, ArrayList<String> following);


}
