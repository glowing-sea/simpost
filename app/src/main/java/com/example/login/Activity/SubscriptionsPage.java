package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.login.DataContainer.Me;

import com.example.login.R;

import java.util.ArrayList;

public class SubscriptionsPage extends AppCompatActivity {
    Me me = Me.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions_page);
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.subscriptions);

        //仅测试，最后将allPosts改成数据库中需要显示的post即可

        ArrayList<String> allUsers = new ArrayList<>(me.getFollowing());
        if (allUsers.size() == 0){
            Toast.makeText(SubscriptionsPage.this, "You have not subscribed to anyone yet", Toast.LENGTH_LONG).show();
        }
        allUsers.add("hello");
        allUsers.add("lty");
        allUsers.add("you");
        SubscribeAdapter subscriberAdapter = new SubscribeAdapter(SubscriptionsPage.this,allUsers);
        rvPosts.setAdapter(subscriberAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager((this)));

    }
}