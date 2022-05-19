package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.User;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HamletPostTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserDAOImpl db = new UserDAOImpl(appContext);
    List<String> users = new ArrayList();

    @Test
    public void creatUsers(){
        UserDAOImpl db = new UserDAOImpl(appContext);
        try{
            //read user names from a file
            InputStream inputStream = appContext.getResources().openRawResource(R.raw.usernames);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line == null){
                    continue;
                }
                ///creat user
                db.addUser(line.trim(), (line+"Pwd").trim());
                users.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            assertEquals(1,2);
        }
    }



    @Test
    public void ReadingFile(){
        int i = 0;
        try{
            //get a list of current users
            InputStream inputStream = appContext.getResources().openRawResource(R.raw.usernames);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                users.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            assertEquals(1,2);
        }


        try{
            InputStream inputStream = appContext.getResources().openRawResource(R.raw.hamlet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int index =0;
            String line = reader.readLine();
            while (line != null) {
                index +=1;
                if (index == 50){
                    index = 0;
                }

                //read tha content of the post
                line = reader.readLine();
                if (line == null||line.length()<10){
                    continue;
                }

                //create and add post
                String poster = users.get(index);
                Post current = new Post(poster, line.substring(4,10), line, null, null, null, "testPost", appContext);
                db.addPost(current);
                i +=1;
                System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            assertEquals(1,2);
        }

        System.out.println(Integer.toString(i)+ " posts are added");
    }


}