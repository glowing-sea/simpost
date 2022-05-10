package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        this.setTitle(this.getText(R.string.home));

        // Page transfer method of the bottom navigator
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_ico_posts:
                    finish();
                    startActivity(new Intent(getApplicationContext(), PostsPage.class));
                    this.overridePendingTransition(0, 0);
                    break;
                case R.id.nav_ico_search:
                    finish();
                    startActivity(new Intent(getApplicationContext(), SearchPage.class));
                    this.overridePendingTransition(0, 0);
                    break;
                case R.id.nav_ico_home:
                    break; }
            return false;
        });

        ImageView settingButton;

        // Set up search button
        settingButton = findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toResult = new Intent(getApplicationContext(), SettingPage.class);
                startActivity(toResult);
            }
        });
    }
}