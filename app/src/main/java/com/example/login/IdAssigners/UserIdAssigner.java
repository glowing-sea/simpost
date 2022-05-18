package com.example.login.IdAssigners;

import android.content.Context;
import android.util.Log;

import com.example.login.FileIO.FileRW;
import com.google.gson.Gson;

import java.io.File;

public class UserIdAssigner {
    private final static String TAG = "UserIdAssigner";
    private final static String folder = "IdAssigners";
    private final static String fileName = "UserIdAssigner.json";
    private  static UserIdAssigner instance = null;
    private int nextFreeId;
    private Context context;

    private UserIdAssigner(Integer starting,Context context)
    {
        nextFreeId = starting;
        context = context;
    }

    public static UserIdAssigner getInstance(Context context) {
        if (instance == null){
            String path = context.getFilesDir() +"/"+folder+"/"+fileName;
            File checkExist = new File(path);
            if (checkExist.exists()){
                instance = readSelf(context);
            }else {
                Log.w(TAG,"UserIdassigner do not exist yet, creating new one");
                instance = new UserIdAssigner(0,context);
            }
            return instance;
        }else{
            return instance;
        }
    }

    public int assignID(Context context) {
        int rtn = nextFreeId;
        nextFreeId = nextFreeId + 1;
        this.saveSelf(context);
        return rtn;
    }

    private void saveSelf(Context context) {
        FileRW fileRW= new FileRW(context);
        File checkFolder= new File(context.getFilesDir(),folder);
        if (!checkFolder.exists()){
            Log.w(TAG,"Folder for assigners do not exist,created");
            checkFolder.mkdir();
        }
        File checkSelf = new File((context.getFilesDir().getPath() +"/" +folder), fileName);
        if(checkSelf.exists()){
            checkSelf.delete();
        }
        fileRW.savingString(folder,fileName,this.toJson());
    }

    private static UserIdAssigner readSelf(Context context) {
        String json = "";
        try {
            FileRW fileRW = new FileRW(context);
            json = fileRW.readJSON(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "unable to read id assigner");
        }
        Gson gson = new Gson();
        UserIdAssigner rtn = gson.fromJson(json, UserIdAssigner.class);
        return rtn;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
