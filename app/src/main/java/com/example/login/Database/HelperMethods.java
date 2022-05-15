package com.example.login.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
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
    static String[] censorWords = {"fuck ", "shit ", "cock ", "titties ", "boner ", "muff ", "pussy ", "asshole ", "cunt ",
            "ass ", "cockfoam ", "nigger ", "damn ", "Fuck ", "Shit ", "Cock ", "Titties ", "Boner ", "Muff ", "Pussy ", "Asshole ", "Cunt ",
            "Ass ", "Cockfoam ", "Nigger ", "Damn "};

    public static String getCensored(String message){
        for (int i = 0; i <= censorWords.length - 1; i++){
            String p = censorWords[i];
            String replace = new String(new char[p.length() - 1]).replace("\0", "*") + " ";
            message = message.replaceAll(p, replace);
        }
        return message;}

}