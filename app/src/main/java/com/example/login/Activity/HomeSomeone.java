package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Someone;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class HomeSomeone extends AppCompatActivity {

    String name;
    ImageView background, avatar;
    TextView username, signature, age, gender, following, followers, location;
    BottomNavigationView nav;
    UserDAO db;
    Bitmap backgroundImage;
    Bitmap avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_someone);

        // Link views
        background = findViewById(R.id.background_someone);
        avatar = findViewById(R.id.avatar_someone);
        username = findViewById(R.id.username_someone);
        signature = findViewById(R.id.signature_someone);
        age = findViewById(R.id.age_someone);
        gender = findViewById(R.id.gender_someone);
        following = findViewById(R.id.following_someone_num);
        followers = findViewById(R.id.followers_someone_num);
        location = findViewById(R.id.location_someone);

        // Get this person's data
        name = getIntent().getStringExtra("USER");
        db = new UserDAOImpl(this);
        Someone s = db.getSomeoneData(name);


        // Set page information according to this person's data
        setTitle(name + "'s Home");
        username.setText(name);
        signature.setText(s.getSignature());

        backgroundImage = s.getBackground();
        if (backgroundImage != null)
            background.setImageBitmap(backgroundImage);
        avatarImage = s.getAvatar();
        if (avatarImage != null)
            avatar.setImageBitmap(avatarImage);

        // Set age
        if (s.getAge() != -1) {
            age.setText(String.valueOf(s.getAge()));
        }

        // Set gender
        Gender genderEnum = s.getGender();
        if (genderEnum != null){
            switch (genderEnum) {
                case MALE:
                    gender.setText("M");
                    break;
                case FEMALE:
                    gender.setText("F");
                    break;
                case OTHER:
                    gender.setText("O");
                    break;
            }
        }

        HashSet<String> followingList = s.getFollowing();
        if (followingList != null){
            following.setText(String.valueOf(s.getFollowing().size()));
        }
        HashSet<String> followersList = s.getFollowers();
        if (followersList != null){
            followers.setText(String.valueOf(s.getFollowers().size()));
        }
        String locationText = s.getLocation();
        if (locationText != null){
            location.setText(s.getLocation());
        }
        if (locationText.isEmpty()){
            location.setText("Not Set");
        }


        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });





        nav = findViewById(R.id.someone_home_bottom_nav);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_message:
                    Intent intent = new Intent(getApplicationContext(), MessagesAddPage.class);
                    intent.putExtra("Receiver",name);
                    startActivity(intent);
                    break;
                case R.id.nav_block:
                    Toast.makeText(getApplicationContext(), "Block", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_follow:
                    Toast.makeText(getApplicationContext(), "Follow", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
    }
}