package com.example.login;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseDAOTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserDAOImpl db = new UserDAOImpl(appContext);

    @Before
    public void setup(){
        db = new UserDAOImpl(appContext);
        db.addUser("TestUser", "123");
    }

//    @After
//    public void finish(){
//        db.deleteUser("TestUser");
//        db.close();
//    }

    @Test
    public void passwordDOATest(){
        db.setPassword("TestUser", "456");
        String actual = db.getPassword("TestUser");
        String expected = "456";
        assertEquals(expected, actual);
    }

    @Test
    public void followingDOATest(){
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual;
        expected.add("Henry");
        expected.add("Amy");
        expected.add("Ben");
        db.setFollowing("TestUser", expected);
        actual = db.getFollowing("TestUser");
        assertEquals(expected, actual);
    }

    @Test
    public void signatureDOATest(){
        db.setSignature("TestUser", "I Love Genshin");
        String actual = db.getSignature("TestUser");
        String expected = "I Love Genshin";
        assertEquals(expected, actual);
    }

    @Test
    public void ageDOATest(){
        db.setAge("TestUser", 16);
        int actual = db.getAge("TestUser");
        int expected = 16;
        assertEquals(expected, actual);
    }

    @Test
    public void genderDOATest(){
        db.setGender("TestUser", 0);
        int actual = db.getGender("TestUser");
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void viewsDOATest(){
        db.setViews("TestUser", 200);
        int actual = db.getViews("TestUser");
        int expected = 200;
        assertEquals(expected, actual);
    }

    @Test
    public void likesDOATest(){
        db.setLikes("TestUser", 300);
        int actual = db.getLikes("TestUser");
        int expected = 300;
        assertEquals(expected, actual);
    }

    @Test
    public void privacySettingsDOATest(){
        ArrayList<Boolean> expected = new ArrayList<>();
        expected.add(true);expected.add(true);expected.add(false);expected.add(true);expected.add(false);
        db.setPrivacySettings("TestUser", expected);
        ArrayList<Boolean> actual = db.getPrivacySettings("TestUser");
        assertEquals(expected, actual);
    }

    @Test
    public void blacklistDOATest(){
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual;
        expected.add("BadGuy1");
        expected.add("BadGuy2");
        expected.add("BadGuy3");
        db.setBlacklist("TestUser", expected);
        actual = db.getBlacklist("TestUser");
        assertEquals(expected, actual);
    }

    @Test
    public void historyDOATest(){
        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> actual;
        expected.add(13213213);
        expected.add(232323);
        expected.add(23213213);
        db.setViewHistory("TestUser", expected);
        actual = db.getViewHistory("TestUser");
        assertEquals(expected, actual);
    }

    @Test
    public void preservedDOATest(){
        db.setPreserved("TestUser", "This is a preserved column");
        String actual = db.getPreserved("TestUser");
        String expected = "This is a preserved column";
        assertEquals(expected, actual);
    }
}