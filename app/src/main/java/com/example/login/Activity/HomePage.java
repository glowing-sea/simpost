package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.User;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    ImageView homeBackground;
    TextView address, userName;
    User current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        this.setTitle(this.getText(R.string.home));
        userName = findViewById(R.id.username_home);
        Bundle fromCreate = getIntent().getExtras();
        if (fromCreate != null){
            current = (User) getIntent().getExtras().getSerializable("USER");
        }
        String name = current.getUsername();
        userName.setText(name);

        getIntent().removeExtra("USER");

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
        settingButton = findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toResult = new Intent(getApplicationContext(), SettingPage.class);
                startActivity(toResult);
            }
        });

        // Log out button
        ImageView logoutButton;
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent toResult = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(toResult);
            }
        });

        address = findViewById(R.id.image_address);

        FloatingActionButton changeBackground;
        homeBackground = findViewById(R.id.homeBackground);
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