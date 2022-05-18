package com.example.simpostapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.simpostapp.DataContainer.Post;
import com.example.simpostapp.DataContainer.Me;
import com.example.simpostapp.Database.HelperMethods;
import com.example.simpostapp.Database.UserDAO;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostCreate extends AppCompatActivity {
    private static final String TAG = "Activity_CreatPost";
    String poster = Me.getInstance().getUsername();
    Button posting;
    TextView tag;
    EditText userInput, title;
    ImageView image1, image2, image3;
    FloatingActionButton add1, add2, add3;
    Bitmap i1, i2, i3;
    UserDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic set up

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);
        //getIntent
        db = new UserDAOImpl(getApplicationContext());

        //Posting
        title = findViewById(R.id.postTitle);
        userInput = (EditText) findViewById(R.id.multxt_CreatePost_userInput);
        posting = (Button) findViewById(R.id.btn_CreatePost_posting);
        image1 = findViewById(R.id.post_image_1);
        image2 = findViewById(R.id.post_image_2);
        image3 = findViewById(R.id.post_image_3);
        add1 = findViewById(R.id.add_post_image_1);
        add2 = findViewById(R.id.add_post_image_2);
        add3 = findViewById(R.id.add_post_image_3);
        tag = findViewById(R.id.tag_create);

        tag.setText("UNSELECTED");

        setTitle("Choose a tag here -->");

        posting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                // Rearrange Images. null image image -> image image null
                if (i1 == null && i2 != null && i3 != null){
                    i1 = i2;
                    i2 = i3;
                    i3 = null;
                }

                // Rearrange Images image null image -> image image null
                if (i1 != null && i2 == null && i3 != null){
                    i2 = i3;
                    i3 = null;
                }

                // Rearrange Images null null image -> image null null
                if (i1 == null && i2 == null && i3 != null){
                    i1 = i3;
                    i3 = null;
                }

                // Rearrange Images null image null -> image null null
                if (i1 == null && i2 != null){
                    i1 = i2;
                    i2 = null;
                }


                String tagString = tag.getText().toString();
                String head = title.getText().toString();
                String postContent = userInput.getText().toString();
                //creating file

                Post current = new Post(poster, head, postContent, i1, i2, i3, tagString, getApplicationContext());

                if (head.isEmpty() || postContent.isEmpty()){
                    Toast.makeText(PostCreate.this, "Post title or content can`t be empty", Toast.LENGTH_SHORT).show();
                } else if (tagString.equals("UNSELECTED")){
                    Toast.makeText(PostCreate.this, "Tag can`t be empty", Toast.LENGTH_SHORT).show();
                } else if (db.addPost(current)) {
                    Toast.makeText(PostCreate.this, "Post created successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(PostCreate.this, "Post creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 102);
            }
        });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = data.getData();
        if (resultCode == RESULT_OK && requestCode == 100){
            Uri imageURI = data.getData();
            image1.setImageURI(imageURI);
            i1 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }
        else if (resultCode == RESULT_OK && requestCode == 101){
            Uri imageURI = data.getData();
            image2.setImageURI(imageURI);
            i2 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }
        else if (resultCode == RESULT_OK && requestCode == 102){
            Uri imageURI = data.getData();
            image3.setImageURI(imageURI);
            i3 = HelperMethods.uri2bitmap(imageUri, getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.post_tag_select, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.anime){
            Toast.makeText(getApplicationContext(), "ANIME", Toast.LENGTH_SHORT).show();
            tag.setText("ANIME");
        }
        if (id == R.id.game){
            Toast.makeText(getApplicationContext(), "GAMES", Toast.LENGTH_SHORT).show();
            tag.setText("GAMES");
        }
        if (id == R.id.manga){
            Toast.makeText(getApplicationContext(), "MANGA", Toast.LENGTH_SHORT).show();
            tag.setText("MANGA");
        }
        if (id == R.id.novels){
            Toast.makeText(getApplicationContext(), "LIGHT-NOVELS", Toast.LENGTH_SHORT).show();
            tag.setText("LIGHT-NOVELS");
        }
        if (id == R.id.art){
            Toast.makeText(getApplicationContext(), "ART", Toast.LENGTH_SHORT).show();
            tag.setText("ART");
        }
        if (id == R.id.study){
            Toast.makeText(getApplicationContext(), "STUDY", Toast.LENGTH_SHORT).show();
            tag.setText("STUDY");
        }
        if (id == R.id.music){
            Toast.makeText(getApplicationContext(), "MUSIC", Toast.LENGTH_SHORT).show();
            tag.setText("MUSIC");
        }
        if (id == R.id.memes){
            Toast.makeText(getApplicationContext(), "MEMES", Toast.LENGTH_SHORT).show();
            tag.setText("MEMES");
        }
        if (id == R.id.cosplay){
            Toast.makeText(getApplicationContext(), "COSPLAY", Toast.LENGTH_SHORT).show();
            tag.setText("COSPLAY");
        }
        if (id == R.id.other){
            Toast.makeText(getApplicationContext(), "OTHER", Toast.LENGTH_SHORT).show();
            tag.setText("OTHER");
        }
        return super.onOptionsItemSelected(item);
    }
}