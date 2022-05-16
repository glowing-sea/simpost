package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.UserAdmin;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class FollowerPage extends AppCompatActivity {
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_page);
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.followers);

        //仅测试，最后将allPosts改成数据库中需要显示的post即可

        List<String> allUsers = me.getFollowers();
        if (allUsers.size() == 0){
            Toast.makeText(FollowerPage.this, "You don`t have any followers yet", Toast.LENGTH_LONG).show();
        }

        FollowerAdapter followerAdapter = new FollowerAdapter(FollowerPage.this,allUsers);
        rvPosts.setAdapter(followerAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager((this)));

    }
}