package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.login.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeSettingLocation extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_setting_location);

        TextView tip = findViewById(R.id.get_location_wait);
        Button cancel = findViewById(R.id.cancel_get_location);
        Button relocate = findViewById(R.id.relocate);

        // Set location listener and manager.
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String cityName = getCityName(latitude, longitude);
                Toast.makeText(getApplicationContext(), "You are at:\n" + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "You are in: " + cityName, Toast.LENGTH_SHORT).show();
                locationManager.removeUpdates(locationListener);
                Intent intent = new Intent().putExtra("CityName", cityName);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        };

        // Check permission
        checkPermission();

        // Open Location Automatically
        getLocation(locationManager, locationListener);

        // Cancel Button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Auto Cancel

        // Relocate Button
        relocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                getLocation(locationManager, locationListener);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    // Step 1
    private void checkPermission(){
        // Check SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Ask Permission
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
                Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "SDK Too Low", Toast.LENGTH_SHORT).show();
        }
    }

    // Step 2
    private void getLocation( LocationManager locationManager, LocationListener locationListener){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Initialisation Failure", Toast.LENGTH_SHORT).show();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            Toast.makeText(getApplicationContext(), "Initialisation Success", Toast.LENGTH_SHORT).show();
        }
    }

    // Step 3
    private String getCityName(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 10);
            if (addressList.size() > 0) {
                for(Address address : addressList){
                    if(address.getLocality() != null && !address.getLocality().isEmpty()){
                        return address.getLocality();

                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}