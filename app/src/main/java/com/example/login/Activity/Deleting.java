package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.DataContainer.Me;
import com.example.login.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Deleting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting);

        String from = getIntent().getStringExtra("DELETE");

        if (from == null)
            finish();

        if (from.equals("AllMessages")){
            Me m = Me.getInstance();
            boolean result = m.setMessages(new ArrayList<>());
            if (result)
                Toast.makeText(getApplicationContext(), "All Messages Have Been Deleted!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Delete Messages Failed!", Toast.LENGTH_SHORT).show();

            Intent toResult = new Intent(getApplicationContext(), Messages.class);
            overridePendingTransition(0, 0);
            startActivity(toResult);
            finish();
        }
        finish();
    }
}