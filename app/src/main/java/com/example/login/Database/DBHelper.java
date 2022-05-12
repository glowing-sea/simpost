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


public class DBHelper extends SQLiteOpenHelper {

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
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                        COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }


    // Whenever we upgrade the table, drop the current table and create a new one.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, context.getText(R.string.user_existed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getText(R.string.added_successfully), Toast.LENGTH_SHORT).show();
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



    // Insert a user into the database
//    public void addUser (String username, String password){
//        // Get database
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Initialise content values
//        ContentValues contentValues = new ContentValues();
//        // Insert values
//        contentValues.put("username", username);
//        contentValues.put("password", password);
//        // Insert values into the database
//        db.insertWithOnConflict("user", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//        db.close();
//    }

    // Update a user's password
    public void updatePassword (String username, String newPassword){
        // Get database
        SQLiteDatabase db = this.getWritableDatabase();
        // Query
        String sQuery = "UPDATE user SET password = ? WHERE username = ?";
        String[] replace = {newPassword,username};
        db.execSQL(sQuery, replace);
        db.close();
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
}
