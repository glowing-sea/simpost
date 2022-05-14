package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.Database.SearchFacade;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

public class LoginPage extends AppCompatActivity {


    UserDAO db;
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
        SearchFacade searchFacade = new SearchFacade(getApplicationContext());
        int result = searchFacade.loginAuthentication(username, password);
        switch (result) {
            case 0: {
                Me me = Me.getInstance();
                me.username = username;
                me.context = this;
                Intent in = new Intent(LoginPage.this, PostsPage.class);
                startActivity(in);
                finish();
            }
            break;
            case -1:
                Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(this, "Username Not Found", Toast.LENGTH_SHORT).show();
                break;
            case -3:
                Toast.makeText(this, "Database Access Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAdmin(View view) {
        Intent i = new Intent(getApplicationContext(), AdminPage.class);
        startActivity(i);
    }
}
