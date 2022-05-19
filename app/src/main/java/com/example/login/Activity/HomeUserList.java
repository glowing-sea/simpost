package com.example.login.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.User;
import com.example.login.DataContainer.UserPreview;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class HomeUserList extends AppCompatActivity {

    RecyclerView recyclerView;
    UserDAO db;
    ArrayList<UserPreview> users;
    HomeUserListAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_list);

        String from = getIntent().getStringExtra("UserListType");
        String whose = getIntent().getStringExtra("Whose");

        Me me = Me.getInstance();
        User m;
        db = new UserDAOImpl(this);

        if (whose.equals(me.username)) {
            m = me;
        } else {
            m = db.getSomeoneData(whose);
        }

        ArrayList<UserPreview> listContent = new ArrayList<>();

        switch (from) {
            case "Following": {
                setTitle("Following List");
                HashSet<String> followingPeople = m.getFollowing();
                for (String name : followingPeople) {
                    Someone someone = db.getSomeoneData(name);
                    listContent.add(new UserPreview(name, someone.getSignature()));
                }
                if (listContent.isEmpty())
                    if (m.equals(me))
                        Toast.makeText(getApplicationContext(), "You don't have any following people!", Toast.LENGTH_SHORT).show();
                setTitle(m.getUsername() + "'s " + "following list");
                break;
            }
            case "Followers": {
                setTitle("Followers List");
                HashSet<String> followers = m.getFollowers();
                for (String name : followers) {
                    Someone someone = db.getSomeoneData(name);
                    listContent.add(new UserPreview(name, someone.getSignature()));
                }
                if (listContent.isEmpty())
                    if (m.equals(me))
                        Toast.makeText(getApplicationContext(), "You don't have any followers!", Toast.LENGTH_SHORT).show();
                setTitle(m.getUsername() + "'s " + "follower list");
                break;
            }
            case "Blacklist": {
                setTitle("Blacklist");
                HashSet<String> blacklist = m.getBlacklist();
                for (String name : blacklist) {
                    Someone someone = db.getSomeoneData(name);
                    listContent.add(new UserPreview(name, someone.getSignature()));
                }
                if (listContent.isEmpty())
                    if (m.equals(me))
                        Toast.makeText(getApplicationContext(), "Your blacklist is empty", Toast.LENGTH_SHORT).show();
                setTitle(m.getUsername() + "'s " + "blacklist");
                break;
            }
        }

        recyclerView = findViewById(R.id.users_follow_list);
        db = new UserDAOImpl(getApplicationContext());
        userAdapter = new HomeUserListAdapter(HomeUserList.this, this, listContent);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        // rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                recreate();
            }
        }
    }
}