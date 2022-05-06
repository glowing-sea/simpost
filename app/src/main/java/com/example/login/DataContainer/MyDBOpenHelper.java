package com.example.login.DataContainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class MyDBOpenHelper extends SQLiteOpenHelper {


    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, "my.db", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(userid INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20),userpassword VARCHAR(20))");
        db.execSQL("CREATE TABLE post(postid INTEGER PRIMARY KEY AUTOINCREMENT,file VARCHAR(2000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}