package com.example.login.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.login.Activity.Messages;
import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Message;

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

    // Encode and decode privacy setting
    public static int privacyEncode (ArrayList<Boolean> settings){
        int s = 10000001;
        s += settings.get(0) ? 1000000 : 0;
        s += settings.get(1) ? 100000 : 0;
        s += settings.get(2) ? 10000 : 0;
        s += settings.get(3) ? 1000 : 0;
        s += settings.get(4) ? 100 : 0;
        s += settings.get(5) ? 10 : 0;
        return s;
    }
    public static ArrayList<Boolean> privacyDecode (int encodeInt){
        String encode = String.valueOf(encodeInt);
        ArrayList<Boolean> s = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            s.add(encode.charAt(i) == '1');}
        return s;
    }

    // Encode and decode gender
    public static int genderEncode (Gender gender){
        switch(gender){
            case MALE: return 0;
            case FEMALE: return 1;
            case OTHER: return 2;
            default: return -1;
        }
    }

    public static Gender genderDecode (int gender){
        switch(gender){
            case 0: return Gender.MALE;
            case 1: return Gender.FEMALE;
            case 2: return Gender.OTHER;
            default: return Gender.NA;
        }
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

    public static boolean sameMessage(Message m1, Message m2){
        return m1.getSender().equals(m2.getSender()) && m1.getReceiver().equals(m2.getReceiver())
                && m1.getDate().equals(m2.getDate()) && m1.getContent().equals(m2.getContent());
    }

}