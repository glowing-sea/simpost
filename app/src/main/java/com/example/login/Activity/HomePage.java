package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.UserAdmin;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    ImageView homeBackground;
    TextView userName, signature, age, gender, followersNum, followers, followingNum, following;
    UserAdmin current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Me me = Me.getInstance();
        setTitle(me.username + "'s " + "Profile");


        userName = findViewById(R.id.username_me);
        userName.setText(me.username);


        signature = findViewById(R.id.signature_me);
        signature.setText(me.getSignature());



        age = findViewById(R.id.age_me);
        if (me.getAge() != -1)
            age.setText(me.getAge() + "");

        gender = findViewById(R.id.gender_me);

        int genderInt = me.getGender();
        switch (genderInt){
            case 0: gender.setText("M"); break;
            case 1: gender.setText("F");break;
            case 2: gender.setText("O"); break;
        }


        followersNum = findViewById(R.id.followers_me_num);
        followingNum = findViewById(R.id.following_me_num);
        followers = findViewById(R.id.followers_me);
        following = findViewById(R.id.following_me);

        followingNum.setText(me.getFollowing().size() + "");


        followersNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, FollowerPage.class);
                i.putExtra("USER", current);
                startActivity(i);
            }
        });
        followingNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, SubscriptionsPage.class);
                i.putExtra("USER", current);
                startActivity(i);
            }
        });

        // Page transfer method of the bottom navigator
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_ico_posts:
                    startActivity(new Intent(getApplicationContext(), PostsPage.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_search:
                    startActivity(new Intent(getApplicationContext(), SearchPage.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_home:
                    break; }
            return false;
        });


        // Setting Button
        ImageView settingButton;
        settingButton = findViewById(R.id.setting_me);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toResult = new Intent(getApplicationContext(), SettingPage.class);
                toResult.putExtra("USER", current);
                startActivity(toResult);
            }
        });

        // Log out button
        ImageView logoutButton;
        logoutButton = findViewById(R.id.logout_me);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent toResult = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(toResult);
            }
        });


        FloatingActionButton changeBackground;
        homeBackground = findViewById(R.id.background_me);
        changeBackground = findViewById(R.id.changeBackground);

        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                homeBackground.setImageURI(data.getData());

            }
        }
    }
}