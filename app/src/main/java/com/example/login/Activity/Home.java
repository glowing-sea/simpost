package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Me;
import com.example.login.Database.HelperMethods;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Home extends AppCompatActivity {

    ImageView background, avatar, setting, privacy, message,report, blacklist;
    Bitmap backgroundImage, avatarImage;
    TextView userName, signature, age, gender, followersNum, followers, followingNum, following;
    FloatingActionButton changeBackgroundButton;
    ConstraintLayout followingBox, followersBox;
    Me me = Me.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Page transfer method of the bottom navigator
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_ico_posts:
                    startActivity(new Intent(getApplicationContext(), Post.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_search:
                    startActivity(new Intent(getApplicationContext(), SearchPage.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.nav_ico_home:
                    break;
            }
            return false;
        });


        // Link IDs
        report = findViewById(R.id.report_me);
        background = findViewById(R.id.background_someone);
        avatar = findViewById(R.id.avatar_someone);
        changeBackgroundButton = findViewById(R.id.gallery_background);
        userName = findViewById(R.id.location_someone);
        signature = findViewById(R.id.signature_someone);
        age = findViewById(R.id.age_someone);
        gender = findViewById(R.id.gender_someone);
        followersNum = findViewById(R.id.followers_someone_num);
        followingNum = findViewById(R.id.following_someone_num);
        followers = findViewById(R.id.followers_other);
        following = findViewById(R.id.following_other);
        setting = findViewById(R.id.setting_me);
        privacy = findViewById(R.id.privacy_me);
        message = findViewById(R.id.message_me);
        followingBox = findViewById(R.id.following_box);
        followersBox = findViewById(R.id.followers_box);
        blacklist = findViewById(R.id.blacklist_me);

        // =========================== SETTING TEXT AND PICTURE ================================= //

        // Set title
        setTitle(me.getUsername() + "'s " + "Profile");

        // Set background and avatar
        backgroundImage = me.getBackground();
        if (backgroundImage != null)
            background.setImageBitmap(backgroundImage);
        avatarImage = me.getAvatar();
        if (avatarImage != null)
            avatar.setImageBitmap(avatarImage);

        // Set username
        userName.setText(me.getUsername());

        // Set signature
        signature.setText(me.getSignature());

        // Set age
        if (me.getAge() != -1) {
            age.setText(String.valueOf(me.getAge()));
        }

        // Set gender
        Gender genderEnum = me.getGender();
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

        // ================================== BUTTONS =========================================== //

        // Setting following and followers
        followingNum.setText(String.valueOf(me.getFollowing().size()));
        followersNum.setText(String.valueOf(me.getFollowers().size()));

        followersBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HomeUserList.class);
                i.putExtra("UserListType", "Followers");
                i.putExtra("Whose", me.username);
                startActivity(i);

            }
        });
        followingBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HomeUserList.class);
                i.putExtra("UserListType", "Following");
                i.putExtra("Whose", me.username);
                startActivity(i);
            }
        });

        blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HomeUserList.class);
                i.putExtra("UserListType", "Blacklist");
                i.putExtra("Whose", me.username);
                startActivity(i);
            }
        });



        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toResult = new Intent(getApplicationContext(), HomePrivacy.class);
                startActivity(toResult);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, HomeSetting.class);
                startActivityForResult(intent, 100);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Messages.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportPage.class);
                intent.putExtra("userName",me.getUsername());
                startActivity(intent);
            }
        });

        // Log out button
        ImageView logoutButton;
        logoutButton = findViewById(R.id.logout_me);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent toResult = new Intent(getApplicationContext(), GeneralLogin.class);
                startActivity(toResult);
            }
        });

        // Change Avatar Button
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 200);
            }
        });

        // Change Background Button
        changeBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 300);
            }
        });

        // ================================= Check Pending Tasks ===================================
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            boolean re;
            if (requestCode == 100) {
            }
            if (requestCode == 200 || requestCode == 300) {
                Uri imageUri = data.getData();
                Bitmap image = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
                if (requestCode == 300) {
                    re = me.setBackground(image);
                    if (!re) {
                        Toast.makeText(getApplicationContext(), "The maximum image size is 200kb", Toast.LENGTH_SHORT).show();
                    } else {
                        background.setImageBitmap(image);
                    }
                } else {
                    re = me.setAvatar(image);
                    if (!re) {
                        Toast.makeText(getApplicationContext(), "The maximum image size is 200kb", Toast.LENGTH_SHORT).show();
                    } else {
                        avatar.setImageBitmap(image);
                    }
                }
            }
        }
    }
}

// ================================DELETED METHODS=========================================== //


//        cameraBackground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(camera.resolveActivity(getPackageManager()) != null){
//                    startActivityForResult(camera, CAMERA_REQ_CODE);
//                }
//            }
//        });

//    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    boolean re;
//                    if (result.getData() != null && result.getResultCode() == RESULT_OK) {
//                        Uri imageUri = result.getData().getData();
//                        Bitmap image = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
//                        if (changeBackground) {
//                            re = me.setBackground(image);
//                            if (!re) {
//                                Toast.makeText(getApplicationContext(), "The image is too large", Toast.LENGTH_SHORT).show();
//                            } else {
//                                background.setImageBitmap(image);
//                            }
//                        } else {
//                            re = me.setAvatar(image);
//                            if (!re) {
//                                Toast.makeText(getApplicationContext(), "The image is too large", Toast.LENGTH_SHORT).show();
//                            } else {
//                                avatar.setImageBitmap(image);
//                            }
//                        }
//                    }
//                }
//            });
