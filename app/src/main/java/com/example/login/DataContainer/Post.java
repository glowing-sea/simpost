package com.example.login.DataContainer;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.login.Database.HelperMethods;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.FileIO.FileRW;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

// A temporary object holding the partial information of a post.
public class Post{
    public final int postID; // Auto initialised
    public final String creator;
    public final String title;
    public final String content;
    public final String date; // Auto initialised
    public final Bitmap image1;
    public final Bitmap image2;
    public final Bitmap image3;
    public final String tag;
    private HashSet<String> likes; // Auto initialised
    private HashSet<String> views; // Auto initialised
    private ArrayList<Comment> comments; // Auto initialised
    public Context context;

    // Don't use this method to create post.
    // This method is designed for the database to generate a post object only.
    public Post(int postID, String creator, String title, String content, String date,
                Bitmap image1, Bitmap image2, Bitmap image3, String tag, HashSet<String> likes,
                HashSet<String> views, ArrayList<Comment> comments, Context context) {
        this.postID = postID;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.date = date;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.tag = tag;
        this.likes = likes;
        this.views = views;
        this.comments = comments;
        this.context = context;
    }

    // This method is designed for create a new post. After creating a post, use addPost method in
    // UserDAO to store this post into the data base.

    /**
     * this creat a post
     * @param creator the name of creator
     * @param title title of post
     * @param content content of post
     * @param image1 image1
     * @param image2 image2
     * @param image3 image3
     * @param tag tag of post
     * @param context context (used for accessing database)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Post(String creator, String title, String content, Bitmap image1,
                Bitmap image2, Bitmap image3, String tag, Context context) {
        this.postID = -1; // The id value will be generated automatically when be stored in the database.
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.date = HelperMethods.getDateTime();
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.tag = tag;
        this.context = context;
    }

    public boolean setLikes(HashSet<String> likes) {
        UserDAO db = new UserDAOImpl(context);
        this.likes = likes;
        return db.setLikes(postID, likes);
    }

    public boolean setViews(HashSet<String> views) {
        UserDAO db = new UserDAOImpl(context);
        this.views = views;
        return db.setViews(postID, views);
    }

    public boolean setComments(ArrayList<Comment> comments) {
        UserDAO db = new UserDAOImpl(context);
        this.comments = comments;
        return db.setComments(postID, comments);
    }

    public boolean addLikes(String username) {
        UserDAO db = new UserDAOImpl(context);
        this.likes.add(username);
        return db.addLikes(postID, username);
    }

    public boolean addViews(String username) {
        UserDAO db = new UserDAOImpl(context);
        this.views.add(username);
        return db.addViews(postID, username);
    }

    public boolean addComments(Comment comment) {
        UserDAO db = new UserDAOImpl(context);
        this.comments.add(comment);
        return db.addComments(postID, comment);
    }

    public ArrayList<Comment> getComments() { return comments; }

    public HashSet<String> getLikes() { return likes; }

    public HashSet<String> getViews() { return views; }



    public String getPoster() {
        return this.creator;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }
}







