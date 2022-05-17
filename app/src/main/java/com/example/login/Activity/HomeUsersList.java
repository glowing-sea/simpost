package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.UserPreview;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class HomeUsersList extends AppCompatActivity {

    RecyclerView recyclerView;
    UserDAO db;
    ArrayList<UserPreview> users;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_follow);
        Me m = Me.getInstance();
        String from = getIntent().getStringExtra("UserListType");
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
                    Toast.makeText(getApplicationContext(), "You don't have any following people!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "You don't have any followers!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Your blacklist is empty", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        recyclerView = findViewById(R.id.users_follow_list);
        db = new UserDAOImpl(getApplicationContext());
        userAdapter = new UserAdapter(HomeUsersList.this, this, listContent, null);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        // rvPosts.setLayoutManager(new GridLayoutManager(this, 2));

    }
}