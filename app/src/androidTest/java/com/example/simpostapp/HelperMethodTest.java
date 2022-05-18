package com.example.simpostapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.simpostapp.DataContainer.Comment;
import com.example.simpostapp.DataContainer.Message;
import com.example.simpostapp.Database.HelperMethods;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HelperMethodTest {

    @Test
    public void getDateTimeTest(){
        String date = HelperMethods.getDateTime();
        assertEquals("2022-5-16".substring(0,4), date.substring(0,4));
    }


    @Test
    public void isValidCommentOrMessageTest(){
        String st = "1121212121212121212123423563765433256785436789965434567977865436786547854787" +
                "85634573649059784658699556775896478786475874";
        assertFalse(HelperMethods.isValidCommentOrMessage(st));
        st = "Hello, how are you!";
        assertTrue(HelperMethods.isValidCommentOrMessage(st));
        st = "Hello, how are you`";
        assertFalse(HelperMethods.isValidCommentOrMessage(st));
        st = "Hello~ how are you";
        assertFalse(HelperMethods.isValidCommentOrMessage(st));
    }

    @Test
    public void commentsEncodeDecodeTest() {
        Comment c1 = new Comment("user", "Good Work", "n.d.");
        Comment c2 = new Comment("user", "Good Work", "n.d.");
        Comment c3 = new Comment("user", "Good Work", "n.d.");
        Comment c4 = new Comment("user", "Good Work", "n.d.");
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);
        // Encode, decode, then encode
        String actualEncoded = Comment.commentsEncode(comments);
        ArrayList<Comment> actualDecoded = Comment.commentsDecode(actualEncoded);
        actualEncoded =  Comment.commentsEncode(actualDecoded);
        String expected = "user`Good Work`n.d.~user`Good Work`n.d.~user`Good Work`n.d.~user`Good Work`n.d.~";
        assertEquals(expected, actualEncoded);
        // Empty comments box
        ArrayList<Comment> empty = new ArrayList<>();
        String actual = Comment.commentsEncode(empty);
        assertEquals("", actual);
        empty = Comment.commentsDecode("");
        assertTrue(empty.isEmpty());
    }

    public void messagesEncodeDecodeTest() {
        Message m1 = new Message("user1", "user2", "n.d.", false, "Good Work");
        // Test if it is correctly encoded
        assertEquals(m1, "user1`user2`n.d.`0`Good Work");
        Message m2 = new Message("user3", "user4", "n.d.", true, "Good Morning");
        Message m3 = new Message("user5", "user6", "n.d.", false, "Good Night");
        Message m4 = new Message("user7", "user8", "n.d.", true, "Good Job");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        // Encode, decode, then encode
        String actualEncoded = Message.messagesEncode(messages);
        ArrayList<Message> actualDecoded = Message.messagesDecode(actualEncoded);
        actualEncoded =  Message.messagesEncode(actualDecoded);
        String expected = "user1`user2`n.d.`0`Good Work~user3`user4`n.d.`1`Good Morning~user5`user6`n.d.`0`Good Night~user7`user8`n.d.`Good Job~";
        assertEquals(expected, actualEncoded);
        assertEquals(expected, actualEncoded);
        // Empty messages box
        ArrayList<Message> empty = new ArrayList<>();
        String actual = Message.messagesEncode(empty);
        assertEquals("", actual);
        empty = Message.messagesDecode("");
        assertTrue(empty.isEmpty());
    }
}