package com.example.login.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HelperMethods {

    // Encode and decode an array list
    public static String listEncode (ArrayList<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (String elem : list){
            stringBuilder.append(elem);
            stringBuilder.append(',');
        }
        return stringBuilder.toString();
    }
    public static ArrayList<String> listDecode (String string){
        ArrayList<String> list = new ArrayList<>();
        if (string.isEmpty())
            return list;
        Collections.addAll(list, string.split(","));
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
}