package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login.R;

public class SearchResultPage extends AppCompatActivity {
    EditText searchKey;
    Button confirm;
    TextView result1, result2, result3, result4, result5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        searchKey = findViewById(R.id.reSearch);
        Bundle fromCreate = getIntent().getExtras();
        String t = "";
        if (fromCreate != null){
            t = fromCreate.getString("keyword");
        }
        searchKey.setText(t);
        confirm = findViewById(R.id.confirmResearch);
        result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        result4 = findViewById(R.id.result4);
        result5 = findViewById(R.id.result5);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reload = new Intent(SearchResultPage.this, SearchResultPage.class);
                reload.putExtra("newKeyword", searchKey.getText().toString());
                startActivity(reload);
            }
        });

    }
}