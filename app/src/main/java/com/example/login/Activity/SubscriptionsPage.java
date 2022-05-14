package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.login.DataContainer.UserAdmin;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsPage extends AppCompatActivity {
    UserAdmin current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions_page);
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.subscriptions);

        //仅测试，最后将allPosts改成数据库中需要显示的post即可
        UserAdmin p1 = new UserAdmin("xyz", "1234");
        UserAdmin p2 = new UserAdmin("Post B", "This is content B.");
        UserAdmin p3 = new UserAdmin("Post C", "This is content C.");
        UserAdmin p4 = new UserAdmin("Post D", "This is content D.");
        UserAdmin p5 = new UserAdmin("Post E", "This is content E.");

        List<UserAdmin> allPosts = new ArrayList<>();
        allPosts.add(p1);
        allPosts.add(p2);
        allPosts.add(p3);
        allPosts.add(p4);
        allPosts.add(p5);

        SubscribeAdapter subscriberAdapter = new SubscribeAdapter(SubscriptionsPage.this,allPosts);
        rvPosts.setAdapter(subscriberAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager((this)));

    }
}