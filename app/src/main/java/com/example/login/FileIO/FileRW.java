package com.example.login.FileIO;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

public class FileRW {
    private static final String TAG = "FileRW";
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
     * @param folder the folder the file want to be stored
     * @param fileName "[filename].json"
     * @param string content of the json
     * @return a boolean indicating it is success
     */
    public boolean savingString(String fileName, String string){

        Log.i(TAG,"attemp to save file to");

        //creating file
        File targetFile = new File(context.getFilesDir(),fileName);
        if(targetFile.exists()){
            Log.e(TAG,"the file attemp to write to already exist"+fileName);
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
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(TAG,"uncable to write file");
            e.printStackTrace();
            return false;
        }
        Log.i(TAG,"File saved to /"+fileName);
        return true;
    }

    /**
     * this function reads a json file and return a string of the content,
     * throws exceptions whne file not found etc
     * @param fileName "[filename].json"
     * @return content of the file as a string
     */
    public String readJSON(String fileName) throws Exception{
        String rtn = "";
        String absolutePath = this.context.getFilesDir().getPath() +"/" +fileName;
        Log.i(TAG,"trying to read:"+absolutePath);

        FileInputStream fis = this.context.openFileInput(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error ccurred when opening raw file for reading.o
        } finally {
            rtn = stringBuilder.toString();
        }
        return rtn;
    }

}
