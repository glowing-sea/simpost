package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.login.DataContainer.UserOld;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsPage extends AppCompatActivity {
    UserOld current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions_page);
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.subscriptions);

        //仅测试，最后将allPosts改成数据库中需要显示的post即可
        UserOld p1 = new UserOld("xyz", "1234");
        UserOld p2 = new UserOld("Post B", "This is content B.");
        UserOld p3 = new UserOld("Post C", "This is content C.");
        UserOld p4 = new UserOld("Post D", "This is content D.");
        UserOld p5 = new UserOld("Post E", "This is content E.");

        List<UserOld> allPosts = new ArrayList<>();
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