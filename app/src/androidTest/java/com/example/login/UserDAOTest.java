package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.DataContainer.Gender;
import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Message;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.Someone;
import com.example.login.DataContainer.User;
import com.example.login.Database.UserDAO;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
        db.addUser("ur", "1");
        Me m = Me.getInstance();
        m.makeLocalCopyOfMyData("ur", "1", appContext);

    }

    @After
    public void finish(){
        db.deleteUser("TestUser");
        db.deleteUser("user1");
        db.deleteUser("user2");
        db.deleteUser("ur");
        db.close();
    }


    @Test
    public void passwordDOATest(){
        // Database level
        db.setPassword("TestUser", "456");
        String actual = db.getPassword("TestUser");
        String expected = "456";
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance(); // Current user is "ur".
        m.setPassword("1234");
        assertEquals("1234", m.getPassword());
        assertEquals("1234", db.getPassword("ur"));
    }

    @Test
    public void ageDOATest(){
        // Database level
        assertEquals(-1, db.getAge("TestUser"));
        db.setAge("TestUser", 16);
        int actual = db.getAge("TestUser");
        int expected = 16;
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setAge(20);
        assertEquals(20, m.getAge());
        assertEquals(20, db.getAge("ur"));
    }

    @Test
    public void followingDOATest(){
        // Database level
        assertTrue(db.getFollowing("TestUser").isEmpty());
        HashSet<String> expected = new HashSet<>();
        HashSet<String> actual;
        expected.add("Henry");
        expected.add("Amy");
        expected.add("Ben");
        db.setFollowing("TestUser", expected);
        actual = db.getFollowing("TestUser");
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setFollowing(expected);
        HashSet<String> local = m.getFollowing();
        HashSet<String> database = db.getFollowing("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }


    @Test
    public void getFollowerTest(){
        Me m = Me.getInstance();
        // No followers
        assertTrue(db.getFollowers("ur").isEmpty()); // Database Level
        assertTrue(m.getFollowers().isEmpty()); // User Level

        db.setFollowing("TestUser", new HashSet<>(Arrays.asList("ur", "Someone")));
        db.setFollowing("user1", new HashSet<>(Arrays.asList("ur", "Someone")));
        db.setFollowing("user2", new HashSet<>(Arrays.asList("ur", "Someone")));

        HashSet<String> expectedFollowers = new HashSet<>(Arrays.asList("TestUser", "user1", "user2"));
        assertEquals(expectedFollowers, db.getFollowers("ur")); // Database Level
        assertEquals(expectedFollowers, m.getFollowers()); // User Level

    }

    @Test
    public void signatureDOATest(){
        // Database level
        assertEquals("This is a default signature for everyone!", db.getSignature("TestUser"));
        db.setSignature("TestUser", "I Love Genshin\n*********\n");
        String actual = db.getSignature("TestUser");
        String expected = "I Love Genshin\n*********\n";
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setSignature(expected);
        String local = m.getSignature();
        String database = db.getSignature("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }

    @Test
    public void genderDOATest(){
        // Database level
        assertEquals(Gender.NA, db.getGender("TestUser"));
        db.setGender("TestUser", Gender.MALE);
        Gender actual = db.getGender("TestUser");
        Gender expected = Gender.MALE;
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setGender(expected);
        Gender local = m.getGender();
        Gender database = db.getGender("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }

    @Test
    public void locationDOATest(){
        // Database Level
        assertTrue(db.getLocation("TestUser").isEmpty());
        db.setLocation("TestUser", "Canberra");
        String actual = db.getLocation("TestUser");
        String expected = "Canberra";
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setLocation(expected);
        String local = m.getLocation();
        String database = db.getLocation("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }

    @Test
    public void privacySettingsDOATest(){
        // Database
        ArrayList<Boolean> expected = new ArrayList<>();
        expected.add(true);expected.add(true);expected.add(false);expected.add(true);expected.add(false); expected.add(false);
        db.setPrivacySettings("TestUser", expected);
        ArrayList<Boolean> actual = db.getPrivacySettings("TestUser");
        assertEquals(6, actual.size());
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setPrivacySettings(expected);
        ArrayList<Boolean> local = m.getPrivacySettings();
        ArrayList<Boolean> database = db.getPrivacySettings("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }

    @Test
    public void blacklistDOATest(){
        // Database
        assertTrue(db.getBlacklist("TestUser").isEmpty());
        HashSet<String> expected = new HashSet<>();
        HashSet<String> actual;
        expected.add("BadGuy1");
        expected.add("BadGuy2");
        expected.add("BadGuy3");
        db.setBlacklist("TestUser", expected);
        actual = db.getBlacklist("TestUser");
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setBlacklist(expected);
        HashSet<String> local = m.getBlacklist();
        HashSet<String> database = db.getBlacklist("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
    }

    @Test
    public void historyDOATest(){
        // Empty
        assertTrue(db.getViewHistory("TestUser").isEmpty());
        HashSet<Integer> expected = new HashSet<>();
        HashSet<Integer> actual;
        expected.add(13213213);
        expected.add(232323);
        expected.add(23213213);
        db.setViewHistory("TestUser", expected);
        actual = db.getViewHistory("TestUser");
        assertEquals(expected, actual);

        // User level
        Me m = Me.getInstance();
        m.setViewHistory(expected);
        HashSet<Integer> local = m.getViewHistory();
        HashSet<Integer> database = db.getViewHistory("ur");
        assertEquals(expected, database);
        assertEquals(expected, local);
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
        assertEquals("user1`user2`n.d.`A message from user1 to user2`1~user2`user1`n.d.`A message from user2 to user1`0~",
                Message.messagesEncode(user1Box)); // First Message Read, Second Unread

        // Check user2 messages box
        ArrayList<Message> user2Box = db.getMessages("user2");
        assertEquals(2, user2Box.size()); // Messages box has 2 messages

        assertEquals("user1`user2`n.d.`A message from user1 to user2`0~user2`user1`n.d.`A message from user2 to user1`1~",
                Message.messagesEncode(user2Box)); // First Message Unread, Second Read
    }

    @Test
    public void sendEmptyMessageTest(){
        Message m1to2 = new Message("user1", "user2", "n.d.", false, "");
        Message m2to1 = new Message("user2", "user1", "n.d.", false, "");
        db.sendMessages(m1to2);
        db.sendMessages(m2to1);

        // Check user1 messages box
        ArrayList<Message> user1Box = db.getMessages("user1");
        assertEquals(2, user1Box.size()); // Messages box has 2 messages
        assertEquals("user1`user2`n.d.``1~user2`user1`n.d.``0~",
                Message.messagesEncode(user1Box)); // First Message Read, Second Unread

        // Check user2 messages box
        ArrayList<Message> user2Box = db.getMessages("user2");
        assertEquals(2, user2Box.size()); // Messages box has 2 messages

        assertEquals("user1`user2`n.d.``0~user2`user1`n.d.``1~",
                Message.messagesEncode(user2Box)); // First Message Unread, Second Read
    }
    @Test
    public void getMyDataTest(){
        // Wrong password
        User user = db.getMyData("TestUser", "456");
        assertNull(user);
        // Wrong username
        user = db.getMyData("TestUserNotExist", "456");
        assertNull(user);
        // Correct
        user = db.getMyData("TestUser", "123");
        assertEquals("TestUser", user.getUsername());
        assertEquals(-1, user.getAge());
        assertEquals(Gender.NA, user.getGender());
        assertEquals("", user.getLocation());
        assertEquals("This is a default signature for everyone!", user.getSignature());
        assertNull(user.getAvatar());
        assertNull(user.getBackground());
        assertTrue(user.getFollowing().isEmpty());
        assertTrue(user.getBlacklist().isEmpty());
        assertTrue(user.getHistory().isEmpty());
    }

    @Test
    public void getSomeoneDataTest(){
        // Wrong username
        Someone s = db.getSomeoneData("TestUserNotExist");
        assertNull(s);
        // User who disabled all privacy setting
        db.setPrivacySettings("ur", new ArrayList<>(Arrays.asList(false,false,false,false,false,false)));
        db.setAge("ur", 20);
        s = db.getSomeoneData("ur");
        assertEquals("ur", s.getUsername());
        assertEquals("This is a default signature for everyone!", s.getSignature());
        assertNull(s.getAvatar());
        assertNull(s.getBackground());
        assertTrue(s.getBlacklist().isEmpty());
        assertTrue(s.getFollowing().isEmpty());
        assertTrue(s.getFollowers().isEmpty());
        assertEquals(20, s.getAge());
        assertEquals(Gender.NA, s.getGender());
        assertEquals("", s.getLocation());

        // User who enabled all privacy setting
        db.setPrivacySettings("ur", new ArrayList<>(Arrays.asList(true,true,true,true,true,true)));
        s = db.getSomeoneData("ur");
        assertNull(s.getFollowing());
        assertNull(s.getFollowers());
        assertEquals(-1, s.getAge());
        assertNull(s.getGender());
        assertNull(s.getLocation());
    }


}