package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.login.DataContainer.Post;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import java.util.Set;

public class reportPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int numberOfPost = 0;
        int numberOfLikes = 0;
        int numberOfDislikes=0;
        int numberOfViews = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_activiry);
        Intent from = getIntent();
        String userNmae = from.getExtras().getString("userName");
        Log.i("reportPage","current user is "+userNmae);
        UserDAOImpl userDAO = new UserDAOImpl(getApplicationContext());
        Set<Post> allPost = userDAO.postAuthorMatch(userNmae);


    }
}