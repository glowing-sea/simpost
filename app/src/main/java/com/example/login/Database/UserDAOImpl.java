package com.example.login.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.login.DataContainer.User;
import com.example.login.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class UserDAOImpl extends SQLiteOpenHelper implements UserDAO{

    // https://www.youtube.com/watch?v=dTIkPsPlEyc
    // Main Reference: https://www.youtube.com/watch?v=hJPk50p7xwA

    // https://www.youtube.com/watch?v=VQKq9RHMS_0&t=0s Part 3

    private Context context;
    private static final String DATABASE_NAME = "AppData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    // Database Constructor
    public UserDAOImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = "CREATE TABLE " + TABLE_NAME +
//                        " (" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
//                        COLUMN_PASSWORD + " TEXT);";
        String query = "CREATE TABLE user (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "avatar BLOB DEFAULT NULL, " +
                "background BLOB DEFAULT NULL, " +
                "following TEXT DEFAULT '', " +
                "signature TEXT DEFAULT 'No Signature', " +
                "age INTEGER DEFAULT -1, " +
                "gender INTEGER DEFAULT -1, " +
                "totalViews INTEGER DEFAULT 0, " +
                "totalLikes INTEGER DEFAULT 0, " +
                "viewHistory TEXT DEFAULT '', " +
                "privacySettings INTEGER DEFAULT 1000001, " +
                "blacklist TEXT DEFAULT '', " +
                "messages BLOB DEFAULT NULL, " +
                "preserved TEXT DEFAULT '');";

        db.execSQL(query);
    }


    // Whenever we upgrade the table, drop the current table and create a new one.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }


    public boolean addUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    // A cursor that read all data.
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public Boolean loginAuthentication(String loginUsername, String loginPassword){
        Cursor cursor = readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(context, context.getText(R.string.no_data), Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                if (loginUsername.equals(username)){
                    if (loginPassword.equals(password)){
                        Toast.makeText(context, context.getText(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        Toast.makeText(context, context.getText(R.string.password_incorrect), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            Toast.makeText(context, context.getText(R.string.username_not_found), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    // Delete a user
    public void deleteUser (String username){
        // Get database
        SQLiteDatabase db = this.getWritableDatabase();
        // Query
        String sQuery = "DELETE FROM user WHERE username = ?";
        String[] replace = {username};
        db.execSQL(sQuery, replace);
        db.close();
    }

    // Delete all users
    public void truncateUsers (){
        // Get database
        SQLiteDatabase db = this.getWritableDatabase();
        // Query
        String sQuery1 = "DELETE FROM user";
        String sQuery2 = "DELETE FROM sqlite_sequence where username = user";
        db.execSQL(sQuery1);
        db.execSQL(sQuery2);
        db.close();
    }












    // Password
    public boolean setPassword(String username, String newPassword){
        return setString(username, "username", newPassword, "password", "user");
    }
    public String getPassword(String username) {
        return getString(username, "username", "password", "user");
    }

    // Following
    public boolean setFollowing(String username, ArrayList<String> following){
        return setList(username, "username", following, "following", "user");
    }
    public ArrayList<String> getFollowing(String username) {
        return getList(username, "username", "following", "user");
    }

    // Signature
    public boolean setSignature(String username, String newSignature){
        return setString(username, "username", newSignature, "signature", "user");
    }
    public String getSignature(String username) {
        return getString(username, "username", "signature", "user");
    }

    // Age
    public boolean setAge(String username, int age){
        return setInt(username, "username", age, "age", "user");
    }
    public int getAge(String username) {
        return getInt(username, "username", "age", "user");
    }

    // Gender
    public boolean setGender(String username, int gender){
        return setInt(username, "username", gender, "gender", "user");
    }
    public int getGender(String username) {
        return getInt(username, "username", "gender", "user");
    }

    // Total View
    public boolean setViews(String username, int views){
        return setInt(username, "username", views, "totalViews", "user");
    }
    public int getViews(String username) {
        return getInt(username, "username", "totalViews", "user");
    }

    // Total Like
    public boolean setLikes(String username, int likes){
        return setInt(username, "username", likes, "totalLikes", "user");
    }
    public int getLikes(String username) {
        return getInt(username, "username", "totalLikes", "user");
    }

    // Privacy Setting
    public boolean setPrivacySettings(String username, ArrayList<Boolean> settings){
        int s = 1000001;
        s += settings.get(0) ? 100000 : 0;
        s += settings.get(1) ? 10000 : 0;
        s += settings.get(2) ? 1000 : 0;
        s += settings.get(3) ? 100 : 0;
        s += settings.get(4) ? 10 : 0;
        return setInt(username, "username", s, "privacySettings", "user");
    }
    public ArrayList<Boolean> getPrivacySettings(String username){
        String encode = getInt(username, "username", "privacySettings", "user") + "";
        ArrayList<Boolean> s = new ArrayList<>();
        for (int i = 1; i < 6; i++){
            s.add(encode.charAt(i) == '1');
        }
        return s;
    }

    // Blacklist
    public boolean setBlacklist(String username, ArrayList<String> blacklist){
        return setList(username, "username", blacklist, "blacklist", "user");
    }
    public ArrayList<String> getBlacklist(String username) {
        return getList(username, "username", "blacklist", "user");
    }

    // View History
    public boolean setViewHistory(String username, ArrayList<Integer> historyInt){
        ArrayList<String> historyString = new ArrayList<>();
        for (int elem : historyInt){
            historyString.add(elem + "");
        }
        return setList(username, "username", historyString, "viewHistory", "user");
    }
    public ArrayList<Integer> getViewHistory(String username) {
        ArrayList<String> historyString = getList(username, "username", "viewHistory", "user");
        ArrayList<Integer> historyInt = new ArrayList<>();
        for (String elem : historyString){
            historyInt.add(Integer.parseInt(elem));
        }
        return historyInt;
    }

    // Preserved
    public boolean setPreserved(String username, String newPreserved){
        return setString(username, "username", newPreserved, "preserved", "user");
    }
    public String getPreserved(String username) {
        return getString(username, "username", "preserved", "user");
    }


    // ================================ PRIVATE HELPER METHOD =================================== //

    // Store a List value into a database.
    private boolean setList(String id, String idColumn, ArrayList<String> listValue, String valueColumn, String tableName){
        String stringValue = listEncode(listValue);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, stringValue);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        return result != -1;
    }
    // Get a List value from a database.
    private ArrayList<String> getList(String id, String idColumn, String valueColumn, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        return listDecode(cursor.getString(0));
    }

    // Store a String value into a database.
    private boolean setString(String id, String idColumn, String value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        return result != -1;
    }
    // Get a String value from a database.
    private String getString(String id, String idColumn, String valueColumn, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        return cursor.getString(0);
    }

    // Store a Integer value into a database.
    private boolean setInt(String id, String idColumn, int value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        return result != -1;
    }
    // Get a Integer value from a database.
    private int getInt(String id, String idColumn, String valueColumn, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return -1;
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    // Encode and decode an array list
    private static String listEncode (ArrayList<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (String elem : list){
            stringBuilder.append(elem);
            stringBuilder.append(',');
        }
        return stringBuilder.toString();
    }
    private static ArrayList<String> listDecode (String string){
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, string.split(","));
        System.out.println(list);
        return list;
    }
}
