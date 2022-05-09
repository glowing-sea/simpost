package com.example.login.DataContainer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.login.Activity.LoginPage;

public class SqlMethod {
    MyDBMethod dbMethod = new MyDBMethod(LoginPage.this, "my.db",null,1);
    SQLiteDatabase db = dbMethod.getWritableDatabase();
    public void save (User us){
        db.execSQL("INSERT INTO user(userID,username,password) values(?,?,?)",
                new String[]{String.valueOf(us.getUserId()),us.getUsername(),us.getPassword()});
    }

    public void save (Post po){
        db.execSQL("INSERT INTO post(postID,title,content) values(?,?,?)",
                new String[]{String.valueOf(po.getPostID()),po.getTitle(),po.getContent()});
    }

    public void deleteFromUser(Integer userid)
    {
        db.execSQL("DELETE FROM user WHERE userID = ?",
                new String[]{String.valueOf(userid)});
    }

    public void deleteFromPost(Integer posted)
    {
        db.execSQL("DELETE FROM user WHERE postID = ?",
                new String[]{String.valueOf(posted)});
    }

    public void updateUser(User us)
    {
        db.execSQL("UPDATE user SET username = ?,password = ? WHERE userID = ?",
                new String[]{us.getUsername(),us.getPassword(),String.valueOf(us.getUserId())});
    }

    public void updatePost(Post po)
    {
        db.execSQL("UPDATE post SET title = ?,content = ? WHERE postID = ?",
                new String[]{po.getTitle(),po.getContent(),String.valueOf(po.getPostID())});
    }

    public User find(Integer userid)
    {
        Cursor cursor =  db.rawQuery("SELECT * FROM user WHERE userid = ?",
                new String[]{userid.toString()});
        if(cursor.moveToFirst())
        {
            int UserId = cursor.getInt(cursor.getColumnIndex("UserId"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            return new User(UserId,username,password);
        }
        cursor.close();
        return null;
    }
}
