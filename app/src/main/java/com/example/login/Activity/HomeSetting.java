package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.login.DataContainer.Me;
import com.example.login.R;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                longitude = loc.getLongitude();
                latitude = loc.getLatitude();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates("gps", 1000, 0, locationListener);


        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeSetting.this, "ajabbjk", Toast.LENGTH_LONG).show();
                Geocoder geocoder = new Geocoder(HomeSetting.this, Locale.getDefault());
                try {
                    List<Address> listAddress = geocoder.getFromLocation(longitude, latitude, 1);
                    if (listAddress.size() > 0){
                        location.setText(listAddress.get(0).getCountryName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        Me me = Me.getInstance();
        username.setText(me.getUsername());
        switch (me.getGender()){
            case 0: male.toggle(); break;
            case 1: female.toggle(); break;
            case 2: other.toggle(); break;
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
                    me.setGender(0);
                if(female.isChecked())
                    me.setGender(1);
                if (other.isChecked()){
                    me.setGender(2);
                }
                // Set age (not empty and >= 0)
                if (!age.getText().toString().isEmpty()){
                    int ageInt = Integer.parseInt(age.getText().toString());
                    if (ageInt >= 0){
                        me.setAge(ageInt);}
                    else{
                        Toast.makeText(getApplicationContext(),"Age must be greater or equal than 0", Toast.LENGTH_LONG).show();
                        isSuccess = false;
                    }
                } else{
                    isSuccess = false;
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
                if (isSuccess)
                    finish();
                Intent intent = new Intent(HomeSetting.this, Home.class);
                startActivity(intent);
            }
        });
    }
}