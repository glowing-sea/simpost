package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.login.DataContainer.Comment;
import com.example.login.Database.HelperMethods;

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
        // Empty comment
        ArrayList<Comment> empty = new ArrayList<>();
        String actual = Comment.commentsEncode(empty);
        assertEquals("", actual);
        empty = Comment.commentsDecode("");
        assertTrue(empty.isEmpty());
    }

}