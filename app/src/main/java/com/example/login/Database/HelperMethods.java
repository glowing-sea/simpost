package com.example.login.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

public class HelperMethods {

    // Encode and decode an array list
    public static String listEncode (ArrayList<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (String elem : list){
            stringBuilder.append(elem);
            stringBuilder.append('~');
        }
        return stringBuilder.toString();
    }
    public static ArrayList<String> listDecode (String string){
        ArrayList<String> list = new ArrayList<>();
        if (string.isEmpty())
            return list;
        Collections.addAll(list, string.split("~"));
        return list;
    }

    // Encode and decode an array list
    public static String setEncode (HashSet<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (String elem : list){
            stringBuilder.append(elem);
            stringBuilder.append('~');
        }
        return stringBuilder.toString();
    }
    public static HashSet<String> setDecode (String string){
        HashSet<String> list = new HashSet<>();
        if (string.isEmpty())
            return list;
        Collections.addAll(list, string.split("~"));
        return list;
    }


    public static Bitmap uri2bitmap (Uri uri, Context context) {
        try {
            // InputStream to handle the result
            InputStream imageStream = context.getContentResolver().openInputStream(uri);
            // Decode the image into bitmap
            return BitmapFactory.decodeStream(imageStream);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean isValidUsername (String username){
        if (username == null)
            return false;
        if (username.length() > 20)
            return false;
        for (int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if (!(
                    (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '-') || (c == '_') ||
                            (c >= '0' && c <= '9')
                    ))
                return false;
        }
        return true;
    }

    public static boolean isValidCommentOrMessage (String string){
        if (string == null)
            return false;
        if (string.length() > 100)
            return false;
        for (int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if (c == '`' | c == '~')
                return false;
        }
        return true;
    }



    // Convert an image from bitmap to bytearray
    public static byte[] bitmapToByteArray (Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50  , byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Convert an image from bytearray to bitmap
    public static Bitmap byteArrayToBitmap (byte[] byteArray) {
        if (byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDateTime (){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(c.getTime());
    }
}