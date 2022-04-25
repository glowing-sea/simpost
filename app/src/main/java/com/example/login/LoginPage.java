package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    EditText name, password;
    Button login, signup;

    SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        userInfo = getSharedPreferences("info", MODE_PRIVATE);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String p = password.getText().toString();
                if (!userInfo.contains(n)){
                    Toast.makeText(LoginPage.this, "Username doesn`t exist", Toast.LENGTH_LONG).show();
                }
                else if (!userInfo.getString(n, "").equals(p)){
                    Toast.makeText(LoginPage.this, "Username and password doesn`t match", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginPage.this, MainPage.class);
                    startActivity(i);
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setEnabled(false);
                String n = name.getText().toString();
                String p = password.getText().toString();
                SharedPreferences.Editor save = userInfo.edit();
                save.putString(n, p);
                save.commit();
                Toast.makeText(LoginPage.this,"You have successfully signed up", Toast.LENGTH_LONG).show();
            }
        });
        }

    }
