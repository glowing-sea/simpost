package com.example.login.FileIO;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class FileRW {
    private static final String TAG = "FileIO";
    private Context context;
    public FileRW(Context context){
        this.context = context;
    }
    /**
     * This fuction makes a director under a path
     * it do not overwrite existing file
     * @param parent the parent dir of the file
     * @param name the child dir of the file
     * @return a boolean indicating whether success
     */
    public boolean makDir(String parent,String name){
        Log.i(TAG,"Attemp creat dir:" + name + "\n under:"+ parent);
        File testParent = new File(parent);
        if (!testParent.exists() || !testParent.isDirectory()){
            Log.e(TAG,"the parent directory already exist or not a directry");
            return false;
        }
        File creatingDir = new File(parent,name);
       boolean creating =  creatingDir.mkdir();
       if (creating){
           return true;
       }else {
           Log.e(TAG,"problem while mkdir");
           return false;
       }

    }

    /**
     * This function save files to private file space
     * it do not overwrite eixsting file
     * @param path the folder the file want to be stored
     * @param fileName the name of the folder the file want to be stored
     * @param string content of the json
     * @return a boolean indicating it is success
     */
    public boolean savingString(String path, String fileName, String string){
        path = context.getFilesDir().toString() + "/" +path;

        Log.i(TAG,"attemp to save file to" + path);

        //test whether the folder exists
        File testFolder = new File(path);
        if(!testFolder.exists()){
            Log.e(TAG,"the folder:"+path+"\ndo not exist");
            return false;
        }

        //creating file
        File targetFile = new File(path,fileName);
        if(targetFile.exists()){
            Log.e(TAG,"the file attemp to write to already exist"+ path+fileName);
            return false;
        }else {
            try{
                targetFile.createNewFile();
            }catch (IOException e){
                Log.e(TAG,"Unable to create new fild\n"+e.toString());
                e.printStackTrace();
                return false;
            }
        }

        //writting content to file
        try {
            FileOutputStream fos = context.openFileOutput(fileName,context.MODE_PRIVATE);
            fos.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(TAG,"uncable to write file");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
