package com.example.login.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.login.DataContainer.Comment;
import com.example.login.DataContainer.Post;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*

This class contains the important method of initialise the database, get a cursor, and read and set
a specific cell in the database.

 */


// ============================ DATABASE CREATION METHODS ========================================//

public class UserDAOImpl extends SQLiteOpenHelper implements UserDAO{

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
        String query1 = "CREATE TABLE user (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "avatar BLOB DEFAULT NULL, " +
                "background BLOB DEFAULT NULL, " +
                "following TEXT DEFAULT '', " +
                "signature TEXT DEFAULT 'This is a default signature for everyone!', " +
                "age INTEGER DEFAULT -1, " +
                "gender INTEGER DEFAULT -1, " +
                "location TEXT DEFAULT '', " +
                "viewHistory TEXT DEFAULT '', " +
                "privacySettings INTEGER DEFAULT 1000001, " +
                "blacklist TEXT DEFAULT '', " +
                "messages TEXT DEFAULT '');";

        db.execSQL(query1);

        String query2 = "CREATE TABLE post (" +
                "postID INTEGER PRIMARY KEY, " +
                "creator TEXT DEFAULT '', " +
                "title TEXT DEFAULT '', " +
                "content TEXT DEFAULT '', " +
                "date TEXT DEFAULT '', " +
                "image1 BLOB DEFAULT NULL, " +
                "image2 BLOB DEFAULT NULL, " +
                "image3 BLOB DEFAULT NULL, " +
                "tag INTEGER DEFAULT '', " +
                "likes TEXT DEFAULT '', " +
                "views TEXT DEFAULT '', " +
                "comments TEXT DEFAULT '', " +
                "FOREIGN KEY (creator) REFERENCES user(username));";
        db.execSQL(query2);
    }


    // Whenever we upgrade the table, drop the current table and create a new one.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    // ================================ POSTS MANAGEMENT ======================================== //

    public boolean addPost (Post post){
        return addPostSpecifyID (post, -1);
    }

    public boolean addPostSpecifyID (Post post, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (id != -1)
            cv.put("postID", id);
        cv.put("creator", post.creator);
        cv.put("title", post.title);
        cv.put("content", post.content);
        cv.put("date", post.date);
        cv.put("tag", post.tag);

        if (post.image1 != null)
            cv.put("image1", HelperMethods.bitmapToByteArray(post.image1));
        if (post.image2 != null)
            cv.put("image2", HelperMethods.bitmapToByteArray(post.image2));
        if (post.image3 != null)
            cv.put("image3", HelperMethods.bitmapToByteArray(post.image3));

        long result = db.insert("post", null, cv);
        db.close();
        return result != -1;
    }


    public void deletePost (int postID){
        // Get database
        SQLiteDatabase db = this.getWritableDatabase();
        // Query
        String sQuery = "DELETE FROM post WHERE postID = ?";
        String[] replace = {String.valueOf(postID)};
        db.execSQL(sQuery, replace);
        db.close();
    }

    // Be careful of null return of image1-3
    public Post getPost (int postID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM post WHERE postID = ?;";
        String[] replace = {String.valueOf(postID)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        String creator = cursor.getString(1);
        String title = cursor.getString(2);
        String content = cursor.getString(3);
        String date = cursor.getString(4);
        Bitmap image1 = HelperMethods.byteArrayToBitmap(cursor.getBlob(5));
        Bitmap image2 = HelperMethods.byteArrayToBitmap(cursor.getBlob(6));
        Bitmap image3 = HelperMethods.byteArrayToBitmap(cursor.getBlob(7));
        String tag = cursor.getString(8);
        HashSet<String> likes = HelperMethods.setDecode(cursor.getString(9));
        HashSet<String> views = HelperMethods.setDecode(cursor.getString(10));
        ArrayList<Comment> comments = Comment.commentsDecode(cursor.getString(11));
        cursor.close();
        db.close();
        return new Post(postID, creator, title, content, date, image1, image2, image3, tag, likes, views, comments, context);
    }
    //overloading method
    public Post getPost (String postID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM post WHERE postID = ?;";
        String[] replace = {postID};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        String creator = cursor.getString(1);
        String title = cursor.getString(2);
        String content = cursor.getString(3);
        String date = cursor.getString(4);
        Bitmap image1 = HelperMethods.byteArrayToBitmap(cursor.getBlob(5));
        Bitmap image2 = HelperMethods.byteArrayToBitmap(cursor.getBlob(6));
        Bitmap image3 = HelperMethods.byteArrayToBitmap(cursor.getBlob(7));
        String tag = cursor.getString(8);
        HashSet<String> likes = HelperMethods.setDecode(cursor.getString(9));
        HashSet<String> views = HelperMethods.setDecode(cursor.getString(10));
        ArrayList<Comment> comments = Comment.commentsDecode(cursor.getString(11));
        cursor.close();
        db.close();
        return new Post(Integer.parseInt(postID), creator, title, content, date, image1, image2, image3, tag, likes, views, comments, context);
    }

    /**
     * this function use the keywrod to match post with this word in title
     * @param keyword the key word need to match details in fts4
     * @return set of posts
     */
    public Set<Post> postTitleMatch(String keyword){
        Set<Post> result = new HashSet<>();
        //cleaning and creating the tables that is used
        SQLiteDatabase db = this.getReadableDatabase();
        String cleaning = "DROP TABLE IF EXISTS searchResult";
        db.execSQL(cleaning);
        String creat = "CREATE VIRTUAL TABLE searchResult USING fts4(postId,title)";
        db.execSQL(creat);
        String dataInsert= "INSERT INTO searchResult(postId,title) SELECT postID,title FROM post";
        db.execSQL(dataInsert);
        //matching
        String matching = "SELECT * FROM searchResult WHERE title MATCH ?";
        String[] keyWordList =new String[1];
        keyWordList[0] = keyword;
        Cursor cursor = null;
        cursor = db.rawQuery(matching,keyWordList);
        //returning the ids
        if (cursor.getCount() == 0) return null;
        while(cursor.isBeforeFirst()){
            cursor.moveToNext();
        }
        while (!cursor.isAfterLast()){
            result.add(getPost(cursor.getString(0)));
            cursor.moveToNext();
        }
        return result;
    }


    // Don't use the following setting and adding method, use those in Post class.

    public boolean setLikes (int postID, HashSet<String> likes){
        String encode = HelperMethods.setEncode(likes);
        return setString(String.valueOf(postID), "postID", encode, "likes", "post");
    }

    public boolean setViews (int postID, HashSet<String> views){
        String encode = HelperMethods.setEncode(views);
        return setString(String.valueOf(postID), "postID", encode, "views", "post");
    }

    public boolean setComments (int postID, ArrayList<Comment> comments){
        String commentsString = Comment.commentsEncode(comments);
        return setString(String.valueOf(postID), "postID", commentsString, "comments", "post");
    }

    public boolean addLikes (int postID, String username){
        String encode = getString(String.valueOf(postID), "postID", "likes", "post");
        if (encode == null) return false;
        HashSet<String> likes = HelperMethods.setDecode(encode);
        likes.add(username);
        encode = HelperMethods.setEncode(likes);
        return setString(String.valueOf(postID), "postID", encode, "likes", "post");
    }

    public boolean addViews (int postID, String username){
        String encode = getString(String.valueOf(postID), "postID", "views", "post");
        if (encode == null) return false;
        HashSet<String> views = HelperMethods.setDecode(encode);
        views.add(username);
        encode = HelperMethods.setEncode(views);
        return setString(String.valueOf(postID), "postID", encode, "views", "post");
    }

    public boolean addComments (int postID, Comment comment){
        String comments = getString(String.valueOf(postID), "postID", "comments", "post");
        comments = comments + comment + '~';
        return setString(String.valueOf(postID), "postID", comments, "comments", "post");
    }


    // ============================ ADDING AND DELETING USERS =================================== //


    // The following methods access, insert, or delete, a whole row in the database.

    // Add a user
    public int addUser(String username, String password){
        if (!HelperMethods.isValidUsername(username))
            return -2;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);
        return result == -1 ? -1 : 0;
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

    // A cursor that read can some specific columns
    // If columns == [], return a cursor that can read all columns
    public Cursor getCursor(String[] columns, String tableName){
        String query;
        if (columns.length == 0){
            query = "SELECT * FROM " + tableName;
        } else{
            StringBuilder q = new StringBuilder("SELECT ");
            for (String column : columns){
                q.append(column).append(", ");
            }
            q.delete(q.length() - 2, q.length() - 1); // Delete the last ","
            q.append("FROM ");
            q.append(tableName);
            q.append(";");
            query = q.toString();
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    // ======================== SETTING AND GETTING A USER'S ATTRIBUTES ========================= //

    // The following methods access and update a single cell in the database.

    // Don't use the following setting and getting method, use those in Me or Other class.

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
        if (newSignature.length() > 100) return false;
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

    // Location
    public boolean setLocation(String username, String location){
        if (location.length() > 20) return false;
        return setString(username, "username", location, "location", "user");
    }
    public String getLocation(String username) {
        return getString(username, "username", "location", "user");
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

    // Background
    public boolean setBackground(String username, Bitmap background){
        return setImage(username, "username", background, "background", "user");
    }
    public Bitmap getBackground(String username) {
        return getImage(username, "username", "background", "user");
    }

    // Avatar
    public boolean setAvatar(String username, Bitmap avatar){
        return setImage(username, "username", avatar, "avatar", "user");
    }
    public Bitmap getAvatar(String username) {
        return getImage(username, "username", "avatar", "user");
    }

    // ================================ PRIVATE  HELPER METHODS ================================= //

    // These are the helper methods.

    // Store a List value into a database.
    private boolean setList(String id, String idColumn, ArrayList<String> listValue, String valueColumn, String tableName){
        String stringValue = HelperMethods.listEncode(listValue);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, stringValue);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        return result != -1;
    }
    // Get a List value from a database.
    private ArrayList<String> getList(String id, String idColumn, String valueColumn, String tableName) {
        ArrayList<String> output;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        output = HelperMethods.listDecode(cursor.getString(0));
        cursor.close();
        return output;
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
        String output;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        output = cursor.getString(0);
        cursor.close();
        return output;
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
        int output;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return -1;
        cursor.moveToNext();
        output = cursor.getInt(0);
        cursor.close();
        return output;
    }

    // Store a Byte Array value into a database.
    public boolean setByteArray(String id, String idColumn, byte[] value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        return result != -1;
    }

    // Get a Byte Array value from a database (May return null).
    public byte[] getByteArray(String id, String idColumn, String valueColumn, String tableName) {
        byte[] output;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + valueColumn + " FROM " + tableName +" WHERE " + idColumn + " = ?;";
        String[] replace = {id};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1)
            return null;
        cursor.moveToNext();
        output = cursor.getBlob(0);
        cursor.close();
        return output;
    }

    // Store a Image value into a database.
    private boolean setImage(String id, String idColumn, Bitmap image, String valueColumn, String tableName){
        // Create a byte output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress the image
        image.compress(Bitmap.CompressFormat.JPEG, 50  , byteArrayOutputStream);
        // Convert the image into byte array
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        // Check size
        if (imageBytes.length > 200000){
            // Further compress
            return false;
        }
        // Store the byte array into the database
        return setByteArray(id, idColumn, imageBytes, valueColumn, tableName);
    }
    // Get a Byte Array value from a database (May return null).
    private Bitmap getImage(String id, String idColumn, String valueColumn, String tableName) {
        byte[] imageBytes = getByteArray(id, idColumn, valueColumn, tableName);
        if (imageBytes == null) return null;
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public void setMessage(String usname, String mess){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE user SET messages = ? WHERE username = ?",
                new String[]{mess,usname});
        db.close();
    }
}
