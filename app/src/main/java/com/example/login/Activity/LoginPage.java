package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

public class LoginPage extends AppCompatActivity {


    UserDAOImpl db;
    EditText usernameInput, passwordInput;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(this.getText(R.string.welcome));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Database
        db = new UserDAOImpl(getApplicationContext());

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }



    public void onClickSignup(View view) {
        Intent i = new Intent(LoginPage.this, SignupPage.class);
        startActivity(i);
    }

    public void onClickLogin(View view) {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        boolean result = db.loginAuthentication(username, password);
        if (result){
            Intent in = new Intent(LoginPage.this, PostsPage.class);
            startActivity(in);
            finish();
        }

    }

    public void onClickAdmin(View view) {
        Intent i = new Intent(getApplicationContext(), AdminPage.class);
        startActivity(i);
    }
}
