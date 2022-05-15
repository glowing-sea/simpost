package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.R;

import java.util.ArrayList;

public class HomePrivacy extends AppCompatActivity {
    Me current = Me.getInstance();
    Switch age, gender, location, censor;
    ArrayList<Boolean> privacySettings = current.getPrivacySettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_privacy);
        age = findViewById(R.id.switch_hide_age);
        gender = findViewById(R.id.switch_hide_gender);
        location = findViewById(R.id.switch_hide_location);
        censor = findViewById(R.id.hide_message);
        age.setChecked(privacySettings.get(0));
        gender.setChecked(privacySettings.get(1));
        location.setChecked(privacySettings.get(2));
        censor.setChecked(privacySettings.get(3));
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
        censor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacySettings();
                privacySettings.set(3, b);
                current.setPrivacySettings(privacySettings);
            }
        });
    }

}