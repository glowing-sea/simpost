package com.example.login.DataContainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.login.Database.HelperMethods;
import com.example.login.Database.SearchFacade;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import java.util.ArrayList;
import java.util.HashSet;

/*
A local temporary copy of data of the current user
 */
public class Me extends User{

    // ========================== FIELD SETTING AND GETTING METHODS ============================= //

    @SuppressLint("StaticFieldLeak")
    private static Me instance = null;
    public Context context;

    // All attributes of the current user
    public String username;
    private int age;
    private Gender gender;
    private String location;
    private String signature;
    private Bitmap avatar;
    private Bitmap background;
    private HashSet<String> following;
    private HashSet<String> blacklist;
    private HashSet<Integer> history;

    /*
    The following attributes of a user are only stored in the database. No local temporary copy.

    String password (for security)
    ArrayList<Boolean> privacy (for security)
    ArrayList<Message> messages (make sure the messages box is always up to date whenever access)
     */

    private Me (){}

    public static Me getInstance() {
        if (instance == null) {
            instance = new Me();
        }
        return instance;
    }

    /**
     * This method retrieve most data of the current from the database and make a temporary copy in Me class.
     * Used only when login
     * @return true if get data successfully and false if get data failed
     */
    public boolean makeLocalCopyOfMyData(String username, String password, Context context){
        UserDAO db = new UserDAOImpl(context);
        User myData = db.getMyData(username, password);
        if (myData == null)
            return false;
        this.username = username;
        this.age = myData.getAge();
        this.gender = myData.getGender();
        this.location = myData.getLocation();
        this.signature = myData.getSignature();
        this.avatar = myData.getAvatar();
        this.background = myData.getBackground();
        this.following = myData.getFollowing();
        this.blacklist = myData.getBlacklist();
        this.history = myData.getHistory();
        this.context = context;
        return true;
    }

    /**
     * This delete temporary copy of my data
     * Used only when logout
     */
    public void deleteLocalCopyOfMyData(){
        instance = null;
    }


    // ======================== USER DATA ACCESS AND MODIFY METHODS 1 =========================== //

    /*
    For getter method, data is got from Me Class.
    For setter method, data is stored into both Me Class (temporary) and database.
     */


    // Username
    public String getUsername() {
        return username;
    }

    // Age
    public boolean setAge(int age){
        UserDAO db = new UserDAOImpl(context);
        this.age = age;
        return db.setAge(username, age);
    }
    public int getAge() {
        return this.age;
    }

    // Gender
    public boolean setGender(Gender gender){
        UserDAO db = new UserDAOImpl(context);
        this.gender = gender;
        return db.setGender(username, gender);
    }
    public Gender getGender() {
        return this.gender;
    }

    // Location
    public boolean setLocation(String location) {
        UserDAO db = new UserDAOImpl(context);
        this.location = location;
        return db.setLocation(username, location);
    }
    public String getLocation() {
        return this.location;
    }

    // Signature
    public boolean setSignature(String newSignature){
        UserDAO db = new UserDAOImpl(context);
        this.signature = newSignature;
        return db.setSignature(username, newSignature);
    }
    public String getSignature() {
        return this.signature;
    }

    // Avatar
    public boolean setAvatar(Bitmap avatar){
        UserDAO db = new UserDAOImpl(context);
        this.avatar = avatar;
        return db.setAvatar(username, avatar);
    }
    public Bitmap getAvatar(){
        return avatar;
    }

    // Background (Be Careful of Null return!)
    public boolean setBackground(Bitmap background){
        UserDAO db = new UserDAOImpl(context);
        this.background = background;
        return db.setBackground(username, background);
    }
    public Bitmap getBackground(){
        return background;
    }

    // Following
    public boolean setFollowing(HashSet<String> following){
        UserDAO db = new UserDAOImpl(context);
        this.following = following;
        return db.setFollowing(username, following);
    }
    public HashSet<String> getFollowing() {
        return this.following;
    }

    // Blacklist
    public boolean setBlacklist(HashSet<String> blacklist){
        UserDAO db = new UserDAOImpl(context);
        this.blacklist = blacklist;
        return db.setBlacklist(username, blacklist);
    }
    public HashSet<String> getBlacklist(){
        return this.blacklist;
    }

    // View History
    public boolean setViewHistory(HashSet<Integer> history){
        UserDAO db = new UserDAOImpl(context);
        this.history = history;
        return db.setViewHistory(username, history);
    }
    public HashSet<Integer> getViewHistory(){
        return this.history;
    }


    // ======================== USER DATA ACCESS AND MODIFY METHODS 2 =========================== //

    /*
    For getter method, data is got from database.
    For setter method, data is stored into database only.
     */

    // Password
    public boolean setPassword(String newPassword) {
        UserDAO db = new UserDAOImpl(context);
        return db.setPassword(username, newPassword);
    }
    public String getPassword() {
        UserDAO db = new UserDAOImpl(context);
        return db.getPassword(username);
    }

    // Privacy Setting
    public boolean setPrivacySettings(ArrayList<Boolean> settings){
        UserDAO db = new UserDAOImpl(context);
        return db.setPrivacySettings(username, settings);
    }
    public ArrayList<Boolean> getPrivacySettings(){
        UserDAO db = new UserDAOImpl(context);
        return db.getPrivacySettings(username);
    }

    // Message
    public boolean setMessages(ArrayList<Message> messages){
        UserDAO db = new UserDAOImpl(context);
        return db.setMessages(username, messages);
    }
    public ArrayList<Message> getMessages(){
        UserDAO db = new UserDAOImpl(context);
        return db.getMessages(username);
    }
    public int sendMessage(Message message){
        UserDAO db = new UserDAOImpl(context);
        return db.sendMessages(message);
    }

    // ================================= USER REPORT METHODS ==================================== //

    public HashSet<String> getFollowers(){
        UserDAO db = new UserDAOImpl(context);
        return db.getFollowers(username);
    }
}



