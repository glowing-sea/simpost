package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Me;
import com.example.login.Database.HelperMethods;
import com.example.login.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeSetting extends AppCompatActivity {

    TextView username;
    Checkable male, female, other;
    EditText age, location, signature;
    ImageView findLocation;
    Button confirm;
    double longitude;
    double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_setting);
        setTitle("Setting");

        username = findViewById(R.id.setting_username_view);
        male = findViewById(R.id.radioButton_male);
        female = findViewById(R.id.radioButton_female);
        other = findViewById(R.id.radioButton_other);
        age = findViewById(R.id.setting_age);
        location = findViewById(R.id.setting_location);
        signature = findViewById(R.id.setting_signature);
        findLocation = findViewById(R.id.find_location);
        confirm = findViewById(R.id.confirm_setting);

        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeSetting.this, HomeSettingLocation.class);
                startActivityForResult(intent, 100);
            }
        });



        Me me = Me.getInstance();
        username.setText(me.getUsername());
        switch (me.getGender()){
            case MALE: male.toggle(); break;
            case FEMALE: female.toggle(); break;
            case OTHER: other.toggle(); break;
        }
        if (me.getAge() != -1){
            age.setText(me.getAge() + "");
        } else {
            age.setText("");
        }

        location.setText(me.getLocation());
        signature.setText(me.getSignature());


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccess = true;

                // Set gender
                if (male.isChecked())
                    me.setGender(Gender.MALE);
                if(female.isChecked())
                    me.setGender(Gender.FEMALE);
                if (other.isChecked()){
                    me.setGender(Gender.OTHER);
                }

                try{
                    // Set age (not empty and >= 0)
                    if (!age.getText().toString().isEmpty()){
                        int ageInt = Integer.parseInt(age.getText().toString());
                        if (ageInt >= 0){
                            me.setAge(ageInt);}
                        else{
                            Toast.makeText(getApplicationContext(),"Age must be greater or equal than 0", Toast.LENGTH_LONG).show();
                            isSuccess = false;
                        }
                    }
                } catch (Exception exception) {
                    Toast.makeText(getApplicationContext(),"Illegal Input", Toast.LENGTH_LONG).show();
                }

                // Set location
                if (!me.setLocation(location.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Location can only contains at most 20 characters", Toast.LENGTH_LONG).show();
                    isSuccess = false;
                }
                // Set signature
                if (!me.setSignature(signature.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Signature can only contains at most 100 characters", Toast.LENGTH_LONG).show();
                    isSuccess = false;
                }
                if (isSuccess){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 100) {
                try{
                    String cityName = data.getStringExtra("CityName");
                    if (cityName == null){
                        location.setText("Unknown");
                    } else {
                        location.setText(cityName);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    location.setText("Unknown");
                }
            }
        }
    }
}