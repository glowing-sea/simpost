package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.simpostapp.DataContainer.Me;
import com.example.simpostapp.R;

import java.util.ArrayList;

public class HomePrivacy extends AppCompatActivity {
    Me current = Me.getInstance();
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch age, gender, location, censor, following, followers;
    ArrayList<Boolean> privacySettings = current.getPrivacySettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_privacy);
        age = findViewById(R.id.switch_hide_age);
        gender = findViewById(R.id.switch_hide_gender);
        location = findViewById(R.id.switch_hide_location);
        following = findViewById(R.id.switch_hide_following);
        followers = findViewById(R.id.switch_hide_followers);
        censor = findViewById(R.id.switch_hide_abusive_language);


        age.setChecked(privacySettings.get(0));
        gender.setChecked(privacySettings.get(1));
        location.setChecked(privacySettings.get(2));
        following.setChecked(privacySettings.get(3));
        followers.setChecked(privacySettings.get(4));
        censor.setChecked(privacySettings.get(5));


        age.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(0, b);
                current.setPrivacySettings(privacySettings);
            }
        });
        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(1, b);
                current.setPrivacySettings(privacySettings);
            }
        });
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(2, b);
                current.setPrivacySettings(privacySettings);
            }
        });
        following.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(3, b);
                current.setPrivacySettings(privacySettings);
            }
        });
        followers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(4, b);
                current.setPrivacySettings(privacySettings);
            }
        });
        censor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(5, b);
                current.setPrivacySettings(privacySettings);
            }
        });
    }

}