package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.DataContainer.Comment;
import com.example.login.DataContainer.Post;
import com.example.login.Database.HelperMethods;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PostDAOTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserDAOImpl db = new UserDAOImpl(appContext);
    Post e = new Post("TestUser", "Game Review", "This is the content", null, null, null, "RPG", appContext);
    final int POST_ID = 100000;

    @Before
    public void setup(){
        db = new UserDAOImpl(appContext);
        db.addPostSpecifyID(e, POST_ID);
    }

    @After
    public void finish(){
        db.deletePost(POST_ID);
        db.close();
    }

    @Test
    public void getPostTest(){
        Post a = db.getPost(POST_ID);
        assertEquals(e.creator, a.creator);
        assertEquals(e.title, a.title);
        assertEquals(e.content, a.content);
        assertEquals(e.date.substring(0,11), a.date.substring(0,11));
        assertNull(a.image1);
        assertNull(a.image2);
        assertNull(a.image3);
        assertEquals(e.tag, a.tag);
        assertTrue(a.views.isEmpty());
        assertTrue(a.likes.isEmpty());
        assertTrue(a.comments.isEmpty());
    }

    @Test
    public void setLikeViewCommentTest(){
        // Set comments, views, and likes.
        HashSet<String> likes = new HashSet<>();
        likes.add("Jack");
        likes.add("Henry");
        likes.add("Daniel");
        db.setLikes(POST_ID, likes);
        HashSet<String> views = new HashSet<>();
        views.add("Jack");
        views.add("Henry");
        views.add("Daniel");
        db.setViews(POST_ID, views);
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Jack", "Good", "n.d."));
        comments.add(new Comment("Henry", "Great", "n.d."));
        comments.add(new Comment("Daniel", "Well", "n.d."));
        db.setComments(POST_ID, comments);

        // Get comments, views, and likes and check if we get the same things.
        Post actual = db.getPost(POST_ID);
        assertTrue(actual.likes.contains("Jack") && actual.likes.contains("Henry") && actual.likes.contains("Daniel"));
        assertTrue(actual.views.contains("Jack") && actual.views.contains("Henry") && actual.views.contains("Daniel"));
        assertEquals(Comment.commentsEncode(comments), Comment.commentsEncode(actual.comments));
    }

    @Test
    public void addLikeViewCommentTest(){
        // Add comments, views, and likes.
        db.addLikes(POST_ID, "Jack");
        db.addLikes(POST_ID, "Henry");
        db.addLikes(POST_ID, "Daniel");
        db.addViews(POST_ID, "Jack");
        db.addViews(POST_ID, "Henry");
        db.addViews(POST_ID, "Daniel");
        db.addComments(POST_ID, new Comment("Jack", "Good", "n.d."));
        db.addComments(POST_ID, new Comment("Henry", "Great", "n.d."));
        db.addComments(POST_ID, new Comment("Daniel", "Well", "n.d."));

        // Get comments, views, and likes and check if we get the same things.
        Post actual = db.getPost(POST_ID);
        assertTrue(actual.likes.contains("Jack") && actual.likes.contains("Henry") && actual.likes.contains("Daniel"));
        assertTrue(actual.views.contains("Jack") && actual.views.contains("Henry") && actual.views.contains("Daniel"));
        assertEquals("Jack`Good`n.d.~Henry`Great`n.d.~Daniel`Well`n.d.~", Comment.commentsEncode(actual.comments));
    }
}