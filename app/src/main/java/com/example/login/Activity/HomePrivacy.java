package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.R;

import java.util.ArrayList;

public class HomePrivacy extends AppCompatActivity {
    Me current = Me.getInstance();
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch age, gender, location, censor, following, followers;
    ArrayList<Boolean> privacySettings = current.getPrivacy();

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
        EditText oldPassword = findViewById(R.id.home_setting_old_password);
        EditText newPassword = findViewById(R.id.home_setting_new_password);
        Button confirmPassword = findViewById(R.id.home_setting_confiem_password);


        age.setChecked(privacySettings.get(0));
        gender.setChecked(privacySettings.get(1));
        location.setChecked(privacySettings.get(2));
        following.setChecked(privacySettings.get(3));
        followers.setChecked(privacySettings.get(4));
        censor.setChecked(privacySettings.get(5));


        age.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(0, b);
                current.setPrivacy(privacySettings);
            }
        });
        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(1, b);
                current.setPrivacy(privacySettings);
            }
        });
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(2, b);
                current.setPrivacy(privacySettings);
            }
        });
        following.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(3, b);
                current.setPrivacy(privacySettings);
            }
        });
        followers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(4, b);
                current.setPrivacy(privacySettings);
            }
        });
        censor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privacySettings = current.getPrivacy();
                privacySettings.set(5, b);
                current.setPrivacy(privacySettings);
            }
        });

        confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = oldPassword.getText().toString();
                String newP = newPassword.getText().toString();
                String myP = current.getPassword();
                if (!oldP.equals(myP)){
                    Toast.makeText(getApplicationContext(),"Old Password Incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    boolean r = current.setPassword(newP);
                    if (r)
                        Toast.makeText(getApplicationContext(),"Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Password Changed Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}