package com.example.login.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.login.DataContainer.Comment;
import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.PostPreview;
import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.User;
import com.example.login.DataContainer.UserAdmin;
import com.example.login.DataContainer.UserPreview;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
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
                "age INTEGER DEFAULT -1, " +
                "gender INTEGER DEFAULT -1, " +
                "location TEXT DEFAULT '', " +
                "signature TEXT DEFAULT 'This is a default signature for everyone!', " +
                "avatar BLOB DEFAULT NULL, " +
                "background BLOB DEFAULT NULL, " +
                "following TEXT DEFAULT '', " +
                "blacklist TEXT DEFAULT '', " +
                "history TEXT DEFAULT '', " +
                "privacy INTEGER DEFAULT 10000001, " +
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
                "tag TEXT DEFAULT '', " +
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


    public void deleteSomeonePosts(String someone){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM post WHERE creator = ?;";
        String[] replace = {someone};
        db.execSQL(query, replace);
        db.close();
    }


    // Delete all posts
    public void truncatePosts (){
        // Get database
        SQLiteDatabase db = this.getWritableDatabase();
        // Query
        String sQuery1 = "DELETE FROM post";
        // String sQuery2 = "DELETE FROM sqlite_sequence where username = user";
        db.execSQL(sQuery1);
        // db.execSQL(sQuery2);
        db.close();
    }

    /**
     * This function get a posts from the database
     * @param postID the post ID
     * @return the post with the input id or null if there is no such a post
     */
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

    /**
     * This function get selected posts from the database
     * @param postIDs the post IDs
     * @return the post with the input id or null if there is no such a post
     */
    public ArrayList<PostPreview> getSelectPosts (int maxPosts, HashSet<Integer> postIDs){
        StringBuilder idsString = new StringBuilder();
        for (int id : postIDs)
            idsString.append(id).append(",");
        if (idsString.length() != 0)
            idsString.delete(idsString.length() - 1, idsString.length());
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM post WHERE postID in (" + idsString
                +") ORDER BY date DESC, postID DESC LIMIT ?;";
        String[] replace = {String.valueOf(maxPosts)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        ArrayList<PostPreview> allPosts = new ArrayList<>();
        while (cursor.moveToNext()) {
            int postID = cursor.getInt(0);
            String creator = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String date = cursor.getString(4);
            Bitmap image = HelperMethods.byteArrayToBitmap(cursor.getBlob(5));
            String tag = cursor.getString(8);
            allPosts.add(new PostPreview(postID, creator, title, content, date, image, tag));
        }
        cursor.close();
        db.close();
        return allPosts;
    }


    /**
     * This function get all post previews from the database
     * Be careful when use it as the database may contain many posts.
     * @return all posts in the database or null if there is not post in the database
     */
    public ArrayList<PostPreview> getAllPosts (int maxPosts){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM post ORDER BY date DESC, postID DESC LIMIT ?;";
        String[] replace = {String.valueOf(maxPosts)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        ArrayList<PostPreview> allPosts = new ArrayList<>();
        while (cursor.moveToNext()) {
            int postID = cursor.getInt(0);
            String creator = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String date = cursor.getString(4);
            Bitmap image = HelperMethods.byteArrayToBitmap(cursor.getBlob(5));
            String tag = cursor.getString(8);
            allPosts.add(new PostPreview(postID, creator, title, content, date, image, tag));
        }
        cursor.close();
        db.close();
        return allPosts;
    }

    /**
     * This function get posts created by specific people.
     * Be careful when use it as the database may contain many posts.
     * @return all posts in the database or null if there is not post in the database
     */
    public ArrayList<PostPreview> getPeoplePosts(int maxPosts, HashSet<String> following){
        StringBuilder followingString = new StringBuilder();
        for (String name : following)
            followingString.append("'").append(name).append("'").append(",");
        if (followingString.length() != 0)
            followingString.delete(followingString.length() - 1, followingString.length());
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM post WHERE creator in (" + followingString
                +") ORDER BY date DESC, postID DESC LIMIT ?;";
        String[] replace = {String.valueOf(maxPosts)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        ArrayList<PostPreview> allPosts = new ArrayList<>();
        while (cursor.moveToNext()) {
            int postID = cursor.getInt(0);
            String creator = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String date = cursor.getString(4);
            Bitmap image = HelperMethods.byteArrayToBitmap(cursor.getBlob(5));
            String tag = cursor.getString(8);
            allPosts.add(new PostPreview(postID, creator, title, content, date, image, tag));
        }
        cursor.close();
        db.close();
        return allPosts;
    }


    /**
     * this function use the keyword to match post with this word in title
     * @param keyword the key word need to match details in fts4
     * @return set of posts Set<Post> null if no mathing has found
     */
    public Set<Post> postMathchFTS4(String keyword){
        Set<Post> result = new HashSet<>();
        //cleaning and creating the tables that is used
        SQLiteDatabase db = this.getReadableDatabase();
        String cleaning = "DROP TABLE IF EXISTS searchResult";
        db.execSQL(cleaning);
        String creat = "CREATE VIRTUAL TABLE searchResult USING fts4(postId,creator,title,content)";
        db.execSQL(creat);
        String dataInsert= "INSERT INTO searchResult(postId,creator,title,content) SELECT postID,creator,title,content FROM post";
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
            result.add(getPost(cursor.getInt(0)));
            cursor.moveToNext();
        }
        return result;
    }

    /**depricated
     * this function use the keyword to match post with this word in the content of the post
     * @param keyword the key word need to match details in fts4
     * @return set of posts Set<Post> null if no mathing has found
     */
    public Set<Post> postAuthorMatch(String keyword){
        Set<Post> result = new HashSet<>();
        //cleaning and creating the tables that is used
        SQLiteDatabase db = this.getReadableDatabase();
        String cleaning = "DROP TABLE IF EXISTS searchResult";
        db.execSQL(cleaning);
        String creat = "CREATE VIRTUAL TABLE searchResult USING fts4(postId,creator,title,content,likes,views)";
        db.execSQL(creat);
        String dataInsert= "INSERT INTO searchResult(postId,creator,title,content,likes,views) SELECT postID,creator,title,content,likes,views FROM post";
        db.execSQL(dataInsert);
        //matching
        String matching = "SELECT * FROM searchResult WHERE creator = ?";
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
            result.add(getPost(cursor.getInt(0)));
            cursor.moveToNext();
        }
        return result;
    }




    // Don't use the following setting and adding method, use those in Post class.

    public boolean setLikes (int postID, HashSet<String> likes){
        return setSet(String.valueOf(postID), "postID", likes, "likes", "post");
    }

    public boolean setViews (int postID, HashSet<String> views){
        return setSet(String.valueOf(postID), "postID", views, "views", "post");
    }

    public boolean setComments (int postID, ArrayList<Comment> comments){
        String commentsString = Comment.commentsEncode(comments);
        return setString(String.valueOf(postID), "postID", commentsString, "comments", "post");
    }

    public boolean addLikes (int postID, String username){
        HashSet<String> likes = getSet(String.valueOf(postID), "postID", "likes", "post");
        if (likes == null) return false;
        likes.add(username);
        return setSet(String.valueOf(postID), "postID", likes, "likes", "post");
    }

    public boolean addViews (int postID, String username){
        HashSet<String> views = getSet(String.valueOf(postID), "postID", "views", "post");
        if (views == null) return false;
        views.add(username);
        return setSet(String.valueOf(postID), "postID", views, "views", "post");
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
        // String sQuery2 = "DELETE FROM sqlite_sequence where username = user";
        db.execSQL(sQuery1);
        // db.execSQL(sQuery2);
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

    /**
     * Retrieve current user's data from the database
     * @param username the user looking for
     * @param password double check the password
     * @return return all data of the user except password, privacy, and messages.
     */
    public User getMyData (String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE username = ?;";
        String[] replace = {String.valueOf(username)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();
        // Double Check the password
        String pw = cursor.getString(1);
        if (!pw.equals(password))
            return null;
        int age = cursor.getInt(2);
        Gender gender = HelperMethods.genderDecode(cursor.getInt(3));
        String location = cursor.getString(4);
        String signature = cursor.getString(5);
        Bitmap avatar = HelperMethods.byteArrayToBitmap(cursor.getBlob(6));
        Bitmap background = HelperMethods.byteArrayToBitmap(cursor.getBlob(7));
        HashSet<String> following = HelperMethods.setDecode(cursor.getString(8));
        HashSet<String> blacklist = HelperMethods.setDecode(cursor.getString(9));
        HashSet<String> historyString = HelperMethods.setDecode(cursor.getString(10));
        HashSet<Integer> history = new HashSet<>();
        for (String elem : historyString) {
            history.add(Integer.parseInt(elem));
        }
        User user = new User(username, age, gender, location, signature, avatar, background, following, blacklist, history);
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Retrieve someone's data from the database
     * @param username the user looking for
     * @return return limited data of a user based on their privacy setting
     */
    public Someone getSomeoneData (String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE username = ?;";
        String[] replace = {String.valueOf(username)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        if (cursor.getCount() != 1) return null;
        cursor.moveToNext();

        int age;
        Gender gender;
        String location;
        String signature;
        Bitmap avatar;
        Bitmap background;
        HashSet<String> following;
        HashSet<String> followers;
        HashSet<String> blacklist;

        // Get data based on privacy setting
        ArrayList<Boolean> privacy = HelperMethods.privacyDecode(cursor.getInt(11));
        if (privacy.size() != 6)
            return null;
        if (!privacy.get(0))
            age = cursor.getInt(2);
        else
            age = -1;
        if (!privacy.get(1))
            gender = HelperMethods.genderDecode(cursor.getInt(3));
        else
            gender = null;
        if (!privacy.get(2))
            location = cursor.getString(4);
        else
            location = null;
        if (!privacy.get(3))
            following = HelperMethods.setDecode(cursor.getString(8));
        else
            following = null;
        if (!privacy.get(4))
            followers = getFollowers(username);
        else
            followers = null;

        // Get data
        signature = cursor.getString(5);
        avatar = HelperMethods.byteArrayToBitmap(cursor.getBlob(6));
        background = HelperMethods.byteArrayToBitmap(cursor.getBlob(7));
        blacklist = HelperMethods.setDecode(cursor.getString(9));
        Someone someone = new Someone(username, following, followers, signature, age, gender, location, blacklist, avatar, background);
        cursor.close();
        db.close();
        return someone;
    }

    /**
     * Get all users' names and signatures
     * @return return preview of all users
     */
    public ArrayList<UserPreview> getAllUsers(int limit){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username, signature FROM user LIMIT ?;";
        String[] replace = {String.valueOf(limit)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        ArrayList<UserPreview> allUsers = new ArrayList<>();
        while (cursor.moveToNext()) {
            String username = cursor.getString(0);
            String signature = cursor.getString(1);
            allUsers.add(new UserPreview(username, signature));
        }
        cursor.close();
        db.close();
        return allUsers;
    }

    /**
     * Get all users' names and password
     * @return return preview of all users
     */
    public ArrayList<UserAdmin> getAllUsersAdmin(int limit){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username, password FROM user LIMIT ?;";
        String[] replace = {String.valueOf(limit)};
        Cursor cursor = null;
        cursor = db.rawQuery(query, replace);
        ArrayList<UserAdmin> allUsers = new ArrayList<>();
        while (cursor.moveToNext()) {
            String username = cursor.getString(0);
            String password = cursor.getString(1);
            allUsers.add(new UserAdmin(username, password));
        }
        cursor.close();
        db.close();
        return allUsers;
    }


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
    public boolean setFollowing(String username, HashSet<String> following){
        return setSet(username, "username", following, "following", "user");
    }
    public HashSet<String> getFollowing(String username) {
        return getSet(username, "username", "following", "user");
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
    public boolean setGender(String username, Gender gender){
        return setInt(username, "username", HelperMethods.genderEncode(gender), "gender", "user");
    }
    public Gender getGender(String username) {
        return HelperMethods.genderDecode(getInt(username, "username", "gender", "user"));
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
        return setInt(username, "username", HelperMethods.privacyEncode(settings), "privacy", "user");
    }
    public ArrayList<Boolean> getPrivacySettings(String username){
        int encode = getInt(username, "username", "privacy", "user");
        return HelperMethods.privacyDecode(encode);
    }


    // Blacklist
    public boolean setBlacklist(String username, HashSet<String> blacklist){
        return setSet(username, "username", blacklist, "blacklist", "user");
    }
    public HashSet<String> getBlacklist(String username) {
        return getSet(username, "username", "blacklist", "user");
    }

    // View History
    public boolean setViewHistory(String username, HashSet<Integer> historyInt){
        HashSet<String> historyString = new HashSet<>();
        for (int elem : historyInt){
            historyString.add(elem + "");
        }
        return setSet(username, "username", historyString, "history", "user");
    }
    public HashSet<Integer> getViewHistory(String username) {
        HashSet<String> historyString = getSet(username, "username", "history", "user");
        HashSet<Integer> historyInt = new HashSet<>();
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

    // Messages Box
    public boolean setMessages(String username, ArrayList<Message> messages){
        String encode = Message.messagesEncode(messages);
        return setString(username, "username", encode, "messages", "user");
    }
    public ArrayList<Message> getMessages(String username) {
        String encode = getString(username, "username", "messages", "user");
        if (encode == null) return null;
        return Message.messagesDecode(encode);
    }

    // Get followers of a username
    public HashSet<String> getFollowers(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username, following FROM user;";
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        HashSet<String> followers = new HashSet<>();
        if(cursor.getCount() == 0)
            return null;
        while (cursor.moveToNext()){
            String stringList = cursor.getString(1);
            ArrayList<String> someoneFollowingList = HelperMethods.listDecode(stringList);
            if (someoneFollowingList.contains(username)){
                followers.add(cursor.getString(0));
            }
        }
        cursor.close();
        return followers;
    }


    /*
    return 0 success
    return -1 sender not found
    return -2 receiver not found
    return -3 store message fail
    return -4 receiver has blocked sender
    return -5 invalid characters used in the content
     */
    public int sendMessages(Message sent) {
        String sender = sent.getSender();
        String receiver = sent.getReceiver();
        sent.setRead(true);
        String senderCopy = sent.toString();
        sent.setRead(false);
        String receiverCopy = sent.toString();

        // Check content
        if (!HelperMethods.isValidCommentOrMessage(sent.getContent())){
            return -5;
        }

        // Put the message into sender's messages box
        String senderMessagesBox = getString(sender, "username", "messages", "user");
        if (senderMessagesBox == null) return -1;

        // Check receiver's black list
        HashSet<String> blacklist = getBlacklist(receiver);
        if (blacklist == null) return -2;
        if (blacklist.contains(sender))
            return -4;
        senderMessagesBox = senderMessagesBox + senderCopy + '~';

        // Put the message into receiver's messages box
        String receiverMessagesBox = getString(receiver, "username", "messages", "user");
        receiverMessagesBox = receiverMessagesBox + receiverCopy + '~';

        boolean result1 = setString(sender, "username", senderMessagesBox, "messages", "user");
        boolean result2 = setString(receiver, "username", receiverMessagesBox, "messages", "user");

        return result1 && result2 ? 0 : -3;
    }


    // ================================ PRIVATE  HELPER METHODS ================================= //

    // These are the helper methods.

    // Store a String value into a database.
    private boolean setString(String id, String idColumn, String value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        db.close();
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
        db.close();
        return output;
    }

    // Store a Integer value into a database.
    private boolean setInt(String id, String idColumn, int value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        db.close();
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
        db.close();
        return output;
    }

    // Store a Byte Array value into a database.
    public boolean setByteArray(String id, String idColumn, byte[] value, String valueColumn, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put (valueColumn, value);
        long result = db.update(tableName, cv, idColumn + " = ?", new String[]{id});
        db.close();
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
        db.close();
        return output;
    }

    // Store a List value into a database.
    private boolean setList(String id, String idColumn, ArrayList<String> listValue, String valueColumn, String tableName){
        String encode = HelperMethods.listEncode(listValue);
        return setString(id, idColumn, encode, valueColumn, tableName);
    }

    // Get a List value from a database.
    private ArrayList<String> getList(String id, String idColumn, String valueColumn, String tableName) {
        String encode = getString(id, idColumn, valueColumn, tableName);
        if (encode == null) return null;
        return HelperMethods.listDecode(encode);
    }

    // Store a List value into a database.
    private boolean setSet(String id, String idColumn, HashSet<String> listValue, String valueColumn, String tableName){
        String encode = HelperMethods.setEncode(listValue);
        return setString(id, idColumn, encode, valueColumn, tableName);
    }

    // Get a List value from a database.
    private HashSet<String> getSet(String id, String idColumn, String valueColumn, String tableName) {
        String encode = getString(id, idColumn, valueColumn, tableName);
        if (encode == null) return null;
        return HelperMethods.setDecode(encode);
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
}
