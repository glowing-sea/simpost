package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.DataContainer.Someone;
import com.example.login.R;

public class other_home extends AppCompatActivity {
    Someone someone_home;
    ImageView background, avatar, blackList;
    TextView age, gender, follower, following, signature, name;
    Button follow;
    Bitmap back, ava;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_someone);
//        Bundle fromCreate = getIntent().getExtras();
//        if (fromCreate != null){
//            other = new Other(fromCreate.getString("OTHER"), getApplicationContext());
//        }
//        //Set up
//        background = findViewById(R.id.background_other);
//        avatar = findViewById(R.id.avatar_other);
//        follower = findViewById(R.id.followers_other);
//        following = findViewById(R.id.following_other);
//        blackList = findViewById(R.id.blacklist_other);
//        age = findViewById(R.id.age_other);
//        gender = findViewById(R.id.gender_other);
//        signature = findViewById(R.id.signature_other);
//        follow = findViewById(R.id.follow_this_user);
//        name = findViewById(R.id.name_other);
//
//        //Set text
//        ArrayList<Boolean> privacy = other.getPrivacy();
//        back = other.getBackground();
//        if (back != null){
//            background.setImageBitmap(back);
//        }
//        ava = other.getAvatar();
//        if (ava != null){
//            avatar.setImageBitmap(ava);
//        }
//        name.setText(other.username);
//        signature.setText(other.getSignature());
//        // Set age
//        if (other.getAge() != -1 && !privacy.get(0)) {
//            age.setText(other.getAge() + "");
//        }
//
//        // Set gender
//        if ( !privacy.get(1)){
//            int genderInt = other.getGender();
//            switch (genderInt) {
//                case 0:
//                    gender.setText("M");
//                    break;
//                case 1:
//                    gender.setText("F");
//                    break;
//                case 2:
//                    gender.setText("O");
//                    break;
//            }}
//        follow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Me me = Me.getInstance();
//                if (me.getFollowing().contains(other.username)){
//                    Toast.makeText(other_home.this, "you already followed this user", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    ArrayList<String> fl = me.getFollowing();
//                    fl.add(other.username);
//                    me.setFollowing(fl);
//                }
//            }
//        });
//        blackList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Me me = Me.getInstance();
//                if (me.getBlacklist().contains(other.username)){
//                    Toast.makeText(other_home.this, "you already blacklisted this user", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    ArrayList<String> fl = me.getBlacklist();
//                    fl.add(other.username);
//                    me.setBlacklist(fl);
//                }
//            }
//        });




    }
}