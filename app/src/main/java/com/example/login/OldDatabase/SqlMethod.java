package com.example.login.OldDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.login.DataContainer.UserAdmin;
import com.example.login.OldDatabase.MyDBMethod;

public class SqlMethod {
    MyDBMethod dbMethod;
    SQLiteDatabase db = dbMethod.getWritableDatabase();

    public SqlMethod(Context context){
        MyDBMethod dbMethod = new MyDBMethod(context,"my.db",null,1);
        SQLiteDatabase db = dbMethod.getWritableDatabase();
    }


//    public void save (User us){
//        db.execSQL("INSERT INTO user(userID,username,password) values(?,?,?)",
//                new String[]{String.valueOf(us.getUserId()),us.getUsername(),us.getPassword()});
//    }
//
//    public void save (Post po){
//        db.execSQL("INSERT INTO post(postID,title,content) values(?,?,?)",
//                new String[]{String.valueOf(po.getPostID()),po.getTitle(),po.getContent()});
//    }
//
//    public void deleteFromUser(Integer userid)
//    {
//        db.execSQL("DELETE FROM user WHERE userID = ?",
//                new String[]{String.valueOf(userid)});
//    }
//
//    public void deleteFromPost(Integer posted)
//    {
//        db.execSQL("DELETE FROM user WHERE postID = ?",
//                new String[]{String.valueOf(posted)});
//    }
//
//    public void updateUser(User us)
//    {
//        db.execSQL("UPDATE user SET username = ?,password = ? WHERE userID = ?",
//                new String[]{us.getUsername(),us.getPassword(),String.valueOf(us.getUserId())});
//    }
//
//    public void updatePost(Post po)
//    {
//        db.execSQL("UPDATE post SET title = ?,content = ? WHERE postID = ?",
//                new String[]{po.getTitle(),po.getContent(),String.valueOf(po.getPostID())});
//    }


    /**
     * @param userid
     * @return User
     * @Hint If the same username can return different userid due to algorithm, then userid != User.getUserId().
     */
    public UserAdmin find(Integer userid)
    {
        Cursor cursor =  db.rawQuery("SELECT * FROM user WHERE userId = ?",
                new String[]{userid.toString()});
        if(cursor.moveToFirst())
        {
            @SuppressLint("Range") int UId = cursor.getInt(cursor.getColumnIndex("UserId"));
            @SuppressLint("Range") String uname = cursor.getString(cursor.getColumnIndex("username"));
            @SuppressLint("Range") String pword = cursor.getString(cursor.getColumnIndex("password"));
            return new UserAdmin(uname,pword);
        }
        cursor.close();
        return null;
    }
}
