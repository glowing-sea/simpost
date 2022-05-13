package com.example.login.DataContainer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.login.FileIO.FileRW;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class Post {
    private int postID;
    private String poster;
    private String title;
    private String content;
    private String date;
    private Byte[] image1;
    private Byte[] image2;
    private Byte[] image3;
    private String gameName;
    private int like;
    private ArrayList<String> comment;
    private String preserved;















    private static final String TAG = "Post";


    public Post(String title, String content){
        this.title = title;
        this.content = content;
        this.postID = 123;//加入id的算法
    }

    public String getPoster() {return poster; }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public int getPostID(){
        return this.postID;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * this function svae the post as [postID].json file under the [privateDirectory]/post
     * @param context getApplicationContext()
     */
    public void savePost(Context context){
        String postFileName = this.getPostID() + ".json";
        String jsonString = this.toJson();
        FileRW fileRW = new FileRW(context);
        String PRIVATE_DIR = context.getFilesDir().getPath();
        boolean folderExist = new File(PRIVATE_DIR,"post").exists();
        boolean savedFile = false;
        if (!folderExist){
            fileRW.makDir(PRIVATE_DIR,"post");
        }
        savedFile =  fileRW.savingString("post",postFileName,jsonString);
        if(!savedFile){
            Toast.makeText(context, "unable to save", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"file not saved");
        }
        //create file that can write in
        Log.i(TAG,"file created");
    }
}
