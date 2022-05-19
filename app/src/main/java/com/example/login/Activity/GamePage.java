package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class GamePage extends AppCompatActivity {
    Button btn_knock0;
    Button btn_knock1;
    Button btn_sound1;
    Button btn_sound2;
    Button btn_sound3;
    Button btn_sound4;
    Button btn_sound5;
    Button btn_sound6;
    Button btn_sound7;
    Button btn_sound8;
    SoundPool soundPool;
    List<Integer> soundIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        btn_knock0 = findViewById(R.id.btn_game_knock0);
        btn_knock1 = findViewById(R.id.btn_game_knock1);
        btn_sound1 = findViewById(R.id.btn_game_1);
        btn_sound2 = findViewById(R.id.btn_game_2);
        btn_sound3 = findViewById(R.id.btn_game_3);
        btn_sound4 = findViewById(R.id.btn_game_4);
        btn_sound5 = findViewById(R.id.btn_game_5);
        btn_sound6 = findViewById(R.id.btn_game_6);
        btn_sound7 = findViewById(R.id.btn_game_7);
        btn_sound8 = findViewById(R.id.btn_game_8);
        soundPool = new SoundPool.Builder().setMaxStreams(3).build();

        soundIds = new ArrayList<>();
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound1,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound2,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound3,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound4,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound5,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound6,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound7,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.sound8,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.knock0,1));
        soundIds.add(soundPool.load(getApplicationContext(),R.raw.knock1,1));

        btn_sound1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(0),1,1,1,0,1);
            }
        });

        btn_sound2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(1),1,1,1,0,1);
            }
        });
        btn_sound3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(2),1,1,1,0,1);
            }
        });

        btn_sound4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(3),1,1,1,0,1);
            }
        });

        btn_sound5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(4),1,1,1,0,1);
            }
        });

        btn_sound6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(5),1,1,1,0,1);
            }
        });

        btn_sound7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(6),1,1,1,0,1);
            }
        });

        btn_sound8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(7),1,1,1,0,1);
            }
        });

        btn_knock0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(8),1,1,1,0,1);
            }
        });

        btn_knock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundIds.get(9),1,1,1,0,1);
            }
        });

    }
}