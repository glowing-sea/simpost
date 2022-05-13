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

import com.example.login.DataContainer.User;
import com.example.login.Database.DBHelper;
import com.example.login.R;

public class SignupPage extends AppCompatActivity {
    EditText name, password;
    Button confirm;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        db = new DBHelper(getApplicationContext());

        name = findViewById(R.id.signupname);
        password = findViewById(R.id.signuppassword);
        confirm = findViewById(R.id.confirmSignup);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString().trim();
                String p = password.getText().toString().trim();
                db.addUser(n,p);
                Toast.makeText(SignupPage.this,"You have successfully signed up", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}





