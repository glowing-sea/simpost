package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.Database.SearchFacade;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseFacadeTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserDAOImpl db = new UserDAOImpl(appContext);
    SearchFacade sf = new SearchFacade(appContext);

    @Before
    public void setup(){
        db = new UserDAOImpl(appContext);
        db.addUser("Test1", "123");
        db.addUser("Test2", "456");
        db.addUser("Test3", "789");
        db.addUser("Test4", "123");
    }

    @After
    public void finish(){
        db = new UserDAOImpl(appContext);
        db.deleteUser("Test1");
        db.deleteUser("Test2");
        db.deleteUser("Test3");
        db.deleteUser("Test4");
    }

    @Test
    public void loginTest(){
        // Login Successfully
        int result = sf.loginAuthentication("Test1", "123");
        assertEquals(0, result);
        // Username does not exist
        result = sf.loginAuthentication("Test5", "123");
        assertEquals(-2, result);
        // Password Incorrect
        result = sf.loginAuthentication("Test1", "456");
        assertEquals(-1, result);
    }

    @Test
    public void getFollowerTest(){
        // No followers

        assertTrue(sf.getFollowers("Test1").isEmpty());

        db.setFollowing("Test2", new HashSet<>(Arrays.asList("Test1", "Test2")));
        db.setFollowing("Test3", new HashSet<>(Arrays.asList("Test4", "Test3")));
        db.setFollowing("Test4", new HashSet<>(Arrays.asList("Test1", "Test5")));

        ArrayList<String> expectedFollowers = new ArrayList<>();
        expectedFollowers.add("Test2");
        expectedFollowers.add("Test4");

        assertEquals(expectedFollowers, sf.getFollowers("Test1"));
    }
}