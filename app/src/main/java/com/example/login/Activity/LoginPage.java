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
import com.example.login.DataContainer.User;
import com.example.login.Database.DBHelper;
import com.example.login.R;

public class LoginPage extends AppCompatActivity {


    DBHelper db;
    EditText usernameInput, passwordInput;
    String username, password;
    SharedPreferences keepLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(this.getText(R.string.welcome));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        keepLogin = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);

        // Database
        db = new DBHelper(getApplicationContext());

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
            SharedPreferences.Editor saveName = keepLogin.edit();
            saveName.putString("name", username);
            saveName.commit();
            Intent in = new Intent(LoginPage.this, PostsPage.class);
            User current = new User(username, password);
            in.putExtra("USER", current);
            startActivity(in);
            finish();
        }

    }

    public void onClickAdmin(View view) {
        Intent i = new Intent(getApplicationContext(), AdminPage.class);
        startActivity(i);
    }
}
