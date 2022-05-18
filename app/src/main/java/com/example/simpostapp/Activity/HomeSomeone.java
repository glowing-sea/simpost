package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.simpostapp.DataContainer.Gender;
import com.example.simpostapp.DataContainer.Me;
import com.example.simpostapp.DataContainer.Someone;
import com.example.simpostapp.Database.UserDAO;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
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
    ConstraintLayout followingBox, followersBox;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationText;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_someone);

        setResult(RESULT_OK);

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
        followersBox = findViewById(R.id.followers_box_someone);
        followingBox = findViewById(R.id.following_box_someone);

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


        // ================================== BUTTONS =========================================== //

        followersBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followersList != null){
                    Intent i = new Intent(getApplicationContext(), HomeUserList.class);
                    i.putExtra("UserListType", "Followers");
                    i.putExtra("Whose", name);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "The followers list of the current user is hidden", Toast.LENGTH_SHORT).show();
                }

            }
        });
        followingBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followingList != null){
                    Intent i = new Intent(getApplicationContext(), HomeUserList.class);
                    i.putExtra("UserListType", "Following");
                    i.putExtra("Whose", name);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "The following list of the current user is hidden", Toast.LENGTH_SHORT).show();
                }
            }
        });


        nav = findViewById(R.id.someone_home_bottom_nav);
        nav.setOnItemSelectedListener(item -> {
            Me me = Me.getInstance();
            Someone someone = db.getSomeoneData(name);
            switch (item.getItemId()) {
                case R.id.nav_message:
                    if (someone.getBlacklist().contains(me.username)){
                        Toast.makeText(getApplicationContext(), "You are blocked by " + name + "!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MessagesAddPage.class);
                        intent.putExtra("Receiver",name);
                        startActivity(intent);
                    }
                    break;
                case R.id.nav_block:
                    HashSet<String> blacklist = me.getBlacklist();
                    if (blacklist.contains(name)) {
                        Toast.makeText(getApplicationContext(), "You have unblocked this user", Toast.LENGTH_SHORT).show();
                        blacklist.remove(name);
                    } else {
                        Toast.makeText(getApplicationContext(), "You are blocking this user", Toast.LENGTH_SHORT).show();
                        blacklist.add(name);
                    }
                    me.setBlacklist(blacklist);
                    break;
                case R.id.nav_follow:
                    HashSet<String> following = me.getFollowing();
                    if (following.contains(name)) {
                        Toast.makeText(getApplicationContext(), "You have unfollowed this user", Toast.LENGTH_SHORT).show();
                        following.remove(name);
                    } else {
                        Toast.makeText(getApplicationContext(),"You are following this user" , Toast.LENGTH_SHORT).show();
                        following.add(name);
                    }
                    me.setFollowing(following);
                    break;
            }
            return false;
        });
    }
}