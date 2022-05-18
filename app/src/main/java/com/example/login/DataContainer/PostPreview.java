package com.example.login.DataContainer;

import android.graphics.Bitmap;

public class PostPreview {
    public final int postID; // Auto initialised
    public final String creator;
    public final String title;
    public final String content;
    public final String date; // Auto initialised
    public final Bitmap image;
    public final String tag;

    public PostPreview(int postID, String creator, String title, String content,
                       String date, Bitmap image, String tag) {
        this.postID = postID;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = image;
        this.tag = tag;
    }

    public int getPostID() {
        return postID;
    }

    public String getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }
}
