package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.login.Recording.MyMediaRecorder;

import java.io.IOException;

public class soundMeterPage extends AppCompatActivity {
    private final String TAG = "Sound Meter";
    TextView showValue = findViewById(R.id.textView_soundMeterPage_valueDisplay);
    MyMediaRecorder myMediaRecorder;
    Thread recorder;
    Float amp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_meter_page);
        myMediaRecorder = new MyMediaRecorder();
    }
}