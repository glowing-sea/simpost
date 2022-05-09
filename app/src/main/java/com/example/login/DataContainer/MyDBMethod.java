package com.example.login.DataContainer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login.Activity.LoginPage;

public class MyDBMethod extends SQLiteOpenHelper {
    public MyDBMethod(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(userID INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20),password VARCHAR(20))");
        db.execSQL("CREATE TABLE post(postID INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(50),content VARCHAR(2000))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
