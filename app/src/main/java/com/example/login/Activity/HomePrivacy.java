package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.login.DataContainer.Me;
import com.example.login.R;

import java.util.ArrayList;

public class HomePrivacy extends AppCompatActivity {
    Me current = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_privacy);
        ArrayList<Boolean> privacySettings = current.getPrivacySettings();
    }

}