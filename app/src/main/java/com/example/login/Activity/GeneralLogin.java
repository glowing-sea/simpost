package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.Database.SearchFacade;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.FileIO.FileRW;
import com.example.login.R;
import com.example.login.tree.AVLTree;
import com.example.login.tree.AVLTreeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GeneralLogin extends AppCompatActivity {

    SoundPool soundPool;
    List<Integer> soundIds;
    UserDAO db;
    EditText usernameInput, passwordInput;
    String username, password;
    SharedPreferences keepLogin;
    AVLTree tree;
    Gson treeBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(this.getText(R.string.welcome));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AVLTree.class, new AVLTreeAdapter());
        treeBuilder = builder.create();
        FileRW fileRW = new FileRW(getApplicationContext());
        try{
            String treeString = fileRW.readJSON("AVLTree.json");
            tree = treeBuilder.fromJson(treeString,AVLTree.class);
        }catch (Exception e){
            tree = new AVLTree("none".hashCode(),"none","pwd");
            tree.save(getApplicationContext());
        }




        //autido effect
        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        soundIds = new ArrayList<>();
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.loginsucess,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.loginfail,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.start_sign_up,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sign_up_fail,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.welcome,1));
        welComeSouneEffect();

        keepLogin = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);

        // Database
        db = new UserDAOImpl(getApplicationContext());

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }

    private void welComeSouneEffect(){
        soundPool.play(soundIds.get(4),1,1,1,0,1);
    }


    public void onClickSignup(View view) {
        int re;
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        re = db.addUser(username,password);
        switch (re) {
            case 0: {
                soundPool.play(soundIds.get(2),1,1,1,0,1);
                Toast.makeText(getApplicationContext(), "You have successfully signed up", Toast.LENGTH_LONG).show();
                break;
            }
            case -2: {
                soundPool.play(soundIds.get(3),1,1,1,0,1);
                Toast.makeText(getApplicationContext(), "Username can only contains numbers, letter, '_', or '-'. and cannot be empty", Toast.LENGTH_LONG).show();
                break;
            }
            case -1: {
                soundPool.play(soundIds.get(3),1,1,1,0,1);
                Toast.makeText(getApplicationContext(), "Username already existed", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void onClickLogin(View view) {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        String findPassWord = tree.getPassWord(username.hashCode());
        if (!findPassWord.equals(password)){
            Toast.makeText(getApplicationContext(),"treeAuthentification fial",Toast.LENGTH_SHORT).show();
        }

        SearchFacade searchFacade = new SearchFacade(getApplicationContext());
        int result1 = searchFacade.loginAuthentication(username, password);
        switch (result1) {
            case 0: {
                Me me = Me.getInstance();
                boolean result2 = me.makeLocalCopyOfMyData(username, password, this);

                if (result2){
                    this.soundPool.play(soundIds.get(0),1,1,1,0,1);
                    Intent in = new Intent(GeneralLogin.this, Post.class);
                    startActivity(in);
                    finish();
                } else{
                    Toast.makeText(this, "Retrieve user data failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case -1:
                Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                //play sound of fail login
                this.soundPool.play(soundIds.get(1),1,1,1,0,1);
                break;
            case -2:
                Toast.makeText(this, "Username Not Found", Toast.LENGTH_SHORT).show();
                //play sound of fail login
                this.soundPool.play(soundIds.get(1),1,1,1,0,1);
                break;
            case -3:
                Toast.makeText(this, "Database Access Failed", Toast.LENGTH_SHORT).show();
                //play sound of fail login
                this.soundPool.play(soundIds.get(1),1,1,1,0,1);

        }
    }




    public void onClickAdmin(View view) {
        Intent i = new Intent(getApplicationContext(), AdminUser.class);
        startActivity(i);
    }
}
