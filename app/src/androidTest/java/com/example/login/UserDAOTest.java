package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.Post;
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
import java.util.Iterator;
import java.util.Set;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserDAOImpl db = new UserDAOImpl(appContext);

    @Before
    public void setup(){
        db = new UserDAOImpl(appContext);
        db.addUser("TestUser", "123");
        db.addUser("user1", "123");
        db.addUser("user2", "123");
    }

    @After
    public void finish(){
        db.deleteUser("TestUser");
        db.deleteUser("user1");
        db.deleteUser("user2");
        db.deleteUser("user3");
        db.close();
    }


    @Test
    public void passwordDOATest(){
        db.setPassword("TestUser", "456");
        String actual = db.getPassword("TestUser");
        String expected = "456";
        assertEquals(expected, actual);
    }

    @Test
    public void followingDOATest(){
        assertTrue(db.getFollowing("TestUser").isEmpty());
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
        assertEquals("This is a default signature for everyone!", db.getSignature("TestUser"));
        db.setSignature("TestUser", "I Love Genshin\n*********\n");
        String actual = db.getSignature("TestUser");
        String expected = "I Love Genshin\n*********\n";
        assertEquals(expected, actual);
    }

    @Test
    public void ageDOATest(){
        assertEquals(-1, db.getAge("TestUser"));
        db.setAge("TestUser", 16);
        int actual = db.getAge("TestUser");
        int expected = 16;
        assertEquals(expected, actual);
    }

    @Test
    public void genderDOATest(){
        assertEquals(-1, db.getGender("TestUser"));
        db.setGender("TestUser", 0);
        int actual = db.getGender("TestUser");
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void locationDOATest(){
        assertTrue(db.getLocation("TestUser").isEmpty());
        db.setLocation("TestUser", "Canberra");
        String actual = db.getLocation("TestUser");
        String expected = "Canberra";
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
        assertTrue(db.getBlacklist("TestUser").isEmpty());
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
        // Empty
        assertTrue(db.getViewHistory("TestUser").isEmpty());
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
    public void messagesDOATest(){
        // Empty messages box
        assertTrue(db.getMessages("user1").isEmpty());

        // Add some messages into the database
        ArrayList<Message> expected = new ArrayList<>();
        expected.add(new Message("user1", "user2", "n.d.", false, "Message from user1 to user2"));
        expected.add(new Message("user3", "user4", "n.d.", true, "Message from user1 to user2"));
        db.setMessages("user1", expected);

        // Retrieve messages from the database
        ArrayList<Message> actual = db.getMessages("user1");

        // Check Equal
        assertEquals(Message.messagesEncode(expected), Message.messagesEncode(actual));
    }


    @Test
    public void sendMessageTest(){
        Message m1to2 = new Message("user1", "user2", "n.d.", false, "A message from user1 to user2");
        Message m2to1 = new Message("user2", "user1", "n.d.", false, "A message from user2 to user1");
        db.sendMessages(m1to2);
        db.sendMessages(m2to1);

        // Check user1 messages box
        ArrayList<Message> user1Box = db.getMessages("user1");
        assertEquals(2, user1Box.size()); // Messages box has 2 messages
        assertEquals("user1`user2`n.d.`1`A message from user1 to user2~user2`user1`n.d.`0`A message from user2 to user1~",
                Message.messagesEncode(user1Box)); // First Message Read, Second Unread

        // Check user2 messages box
        ArrayList<Message> user2Box = db.getMessages("user2");
        assertEquals(2, user2Box.size()); // Messages box has 2 messages

        assertEquals("user1`user2`n.d.`0`A message from user1 to user2~user2`user1`n.d.`1`A message from user2 to user1~",
                Message.messagesEncode(user2Box)); // First Message Unread, Second Read
    }
}