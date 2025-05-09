package com.example.login.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashSet;

/*

This class contain searching operation through the database.

 */

public class SearchFacade {
    Context context;
    UserDAO db;

    public SearchFacade (Context context){
        this.context = context;
        this.db = new UserDAOImpl(context);
    }

    // Authenticate a user
    public int loginAuthentication(String loginUsername, String loginPassword){
        Cursor cursor = db.getCursor(new String[]{"username", "password"}, "user");
        if(cursor.getCount() == 0){
            return -3;
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                if (loginUsername.equals(username)){
                    if (loginPassword.equals(password)){
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
            return -2;
        }
    }

    // Deprecate
    // Get followers of a username
    public HashSet<String> getFollowers(String username){
        HashSet<String> followers = new HashSet<>();
        Cursor cursor = db.getCursor(new String[]{"username" , "following"}, "user");
        if(cursor.getCount() == 0)
            return null;
        while (cursor.moveToNext()){
            String stringList = cursor.getString(1);
            ArrayList<String> someoneFollowingList = HelperMethods.listDecode(stringList);
            if (someoneFollowingList.contains(username)){
                followers.add(cursor.getString(0));
            }
        }
        cursor.close();
        return followers;
    }
}
