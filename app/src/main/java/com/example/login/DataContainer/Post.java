package com.example.login.DataContainer;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.login.FileIO.FileRW;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Post implements Serializable {
    private int postID;
    private String poster;
    private String title;
    private String content;
    private String date;
    private Byte[] image1;
    private Byte[] image2;
    private Byte[] image3;
    private String tag;
    private String likes;
    private String views;
    private ArrayList<String> comment;


    private static final String TAG = "Post";


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Post(String title, String content){
        this.title = title;
        this.content = content;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date = sdf.format(c.getTime());
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
    public String getDate(){return this.date;}
    public Byte[] getImage1() {
        return image1;
    }

    public Byte[] getImage2() {
        return image2;
    }

    public Byte[] getImage3() {
        return image3;
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
