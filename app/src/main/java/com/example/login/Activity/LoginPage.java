package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.DataContainer.SqlMethod;
import com.example.login.R;

public class LoginPage extends AppCompatActivity {

    EditText name, password;
    SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(this.getText(R.string.welcome_text));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Database
        // SqlMethod sqlMethod = new SqlMethod(LoginPage.this);

        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void onClickSignup(View view) {
        Intent i = new Intent(LoginPage.this, SignupPage.class);
        startActivity(i);
    }

    public void onClickLogin(View view) {
        //为了测试方便我先把这个部分改成了点击login就会跳到mainpage
        Intent in = new Intent(LoginPage.this, MainPage.class);
        startActivity(in);
        userInfo = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String n = name.getText().toString();
        String p = password.getText().toString();
        if (!userInfo.contains(n)) {
            Toast.makeText(LoginPage.this, "Username doesn`t exist", Toast.LENGTH_LONG).show();
        } else if (!userInfo.getString(n, "").equals(p)) {
            Toast.makeText(LoginPage.this, "Username and password doesn`t match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginPage.this, MainPage.class);
            startActivity(i);
        }
    }
}
