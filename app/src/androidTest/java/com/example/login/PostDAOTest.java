package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.DataContainer.Comment;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.PostPreview;
import com.example.login.Database.UserDAOImpl;

import org.junit.After;
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
    Post e = new Post("TestUser", "Game Review", "This is the content", null, null, null, "Anime", appContext);
    final int POST_ID = 100;

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
    public void getFollowingPostTest(){
        Post p1 = new Post("Test1", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        Post p2 = new Post("Test2", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        Post p3 = new Post("Test3", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        db.addPostSpecifyID(p1, 100001);
        db.addPostSpecifyID(p2, 100002);
        db.addPostSpecifyID(p3, 100003);

        HashSet<String> following = new HashSet<>();
        following.add("Test1");
        following.add("Test2");
        following.add("Test3");

        int actual = db.getPeoplePosts(Integer.MAX_VALUE, following).size();
        assertEquals(3, actual);

        db.deletePost(100001);
        db.deletePost(100002);
        db.deletePost(100003);
    }

    @Test
    public void getSelectPostsTest(){
        Post p1 = new Post("Test1", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        Post p2 = new Post("Test2", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        Post p3 = new Post("Test3", "Game Review", "This is the content", null, null, null, "Anime", appContext);
        db.addPostSpecifyID(p1, 100001);
        db.addPostSpecifyID(p2, 100002);
        db.addPostSpecifyID(p3, 100003);
        HashSet<Integer> postIDs = new HashSet<>();
        postIDs.add(100001);
        postIDs.add(100002);
        postIDs.add(100003);

        int actual = db.getSelectPosts(Integer.MAX_VALUE, postIDs).size();
        assertEquals(3, actual);

        db.deletePost(100001);
        db.deletePost(100002);
        db.deletePost(100003);
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
        assertEquals("Anime", a.tag);
        assertEquals(e.tag, a.tag);
        assertTrue(a.getViews().isEmpty());
        assertTrue(a.getLikes().isEmpty());
        assertTrue(a.getComments().isEmpty());
    }

    @Test
    public void getAllPosts(){
        int before = db.getAllPosts(Integer.MAX_VALUE).size();
        Post p1 = new Post("TestUser1", "Game Review1", "This is the content", null, null, null, "Anime", appContext);
        Post p2 = new Post("TestUser2", "Game Review2", "This is the content", null, null, null, "Anime", appContext);
        Post p3 = new Post("TestUser3", "Game Review3", "This is the content", null, null, null, "Anime", appContext);
        db.addPostSpecifyID(p1, 100001);
        db.addPostSpecifyID(p2, 100002);
        db.addPostSpecifyID(p3, 100003);
        // Check Size
        ArrayList<PostPreview> allPosts = db.getAllPosts(Integer.MAX_VALUE);
        PostPreview a1 = allPosts.get(1);
        assertEquals("Anime", a1.tag);
        int after = allPosts.size();
        assertEquals(before + 3, after);
        db.deletePost(100001);
        db.deletePost(100002);
        db.deletePost(100003);
    }




    @Test
    public void setLikeViewCommentTest(){
        Post a = db.getPost(POST_ID);
        // Set comments, views, and likes.
        HashSet<String> likes = new HashSet<>();
        likes.add("Jack");
        likes.add("Henry");
        likes.add("Daniel");
        a.setLikes(likes);
        HashSet<String> views = new HashSet<>();
        views.add("Jack");
        views.add("Henry");
        views.add("Daniel");
        a.setViews(views);
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Jack", "Good", "n.d."));
        comments.add(new Comment("Henry", "Great", "n.d."));
        comments.add(new Comment("Daniel", "Well", "n.d."));
        a.setComments(comments);

        // Check if the comments, views, and likes are stored in the temporary Post class.
        assertTrue(a.getLikes().contains("Jack") && a.getLikes().contains("Henry") && a.getLikes().contains("Daniel"));
        assertTrue(a.getViews().contains("Jack") && a.getViews().contains("Henry") && a.getViews().contains("Daniel"));
        assertEquals(Comment.commentsEncode(comments), Comment.commentsEncode(a.getComments()));

        // Check if the comments, views, and likes are stored in the database.
        Post b = db.getPost(POST_ID);
        assertTrue(b.getLikes().contains("Jack") && b.getLikes().contains("Henry") && b.getLikes().contains("Daniel"));
        assertTrue(b.getViews().contains("Jack") && b.getViews().contains("Henry") && b.getViews().contains("Daniel"));
        assertEquals(Comment.commentsEncode(comments), Comment.commentsEncode(b.getComments()));
    }

    @Test
    public void addLikeViewCommentTest(){
        Post a = db.getPost(POST_ID);
        // Add comments, views, and likes.
        a.addLikes("Jack");
        a.addLikes("Henry");
        a.addLikes("Daniel");
        a.addViews("Jack");
        a.addViews("Henry");
        a.addViews("Daniel");
        a.addComments(new Comment("Jack", "Good", "n.d."));
        a.addComments(new Comment("Henry", "Great", "n.d."));
        a.addComments(new Comment("Daniel", "Well", "n.d."));

        // Check if the comments, views, and likes are stored in the temporary Post class.
        assertTrue(a.getLikes().contains("Jack") && a.getLikes().contains("Henry") && a.getLikes().contains("Daniel"));
        assertTrue(a.getViews().contains("Jack") && a.getViews().contains("Henry") && a.getViews().contains("Daniel"));
        assertEquals("Jack`Good`n.d.~Henry`Great`n.d.~Daniel`Well`n.d.~", Comment.commentsEncode(a.getComments()));

        // Check if the comments, views, and likes are stored in the database.
        Post b = db.getPost(POST_ID);
        assertTrue(b.getLikes().contains("Jack") && b.getLikes().contains("Henry") && b.getLikes().contains("Daniel"));
        assertTrue(b.getViews().contains("Jack") && b.getViews().contains("Henry") && b.getViews().contains("Daniel"));
        assertEquals("Jack`Good`n.d.~Henry`Great`n.d.~Daniel`Well`n.d.~", Comment.commentsEncode(b.getComments()));
    }

//    @Test
//    public void postMatch(){
//        // 这个Post Constructor不会生成ID的，是当Post被放进去Database才会生成ID，如果想知道你放进去的Post的ID是啥（为了在测试结束删掉），用addPostSpecifyID
//        Post test = new Post("Dai","Hello world","this is a testing post"
//                ,null,null,null,null, appContext.getApplicationContext());
//        int id = 100004;
//        db.addPostSpecifyID(test, 100004);
//        System.out.println("Founded");
//        Set<Post> result= db.postMathchFTS4("content:testing");
//        Iterator<Post> iterator = result.iterator();
//        while (iterator.hasNext()){
//            Post current = iterator.next();
//            System.out.println(current.postID);
//            System.out.println(current.creator);
//            System.out.println(current.title);
//            System.out.println(current.content);
//        }
//
//        // 测试完最好把Post删掉
//        db.deletePost(100004);
//    }

//    @Test
//    public void contnetMatchTest(){
//        // 这个Post Constructor不会生成ID的，是当Post被放进去Database才会生成ID，如果想知道你放进去的Post的ID是啥（为了在测试结束删掉），用addPostSpecifyID
//        Post test = new Post("Dai","Hello world","!Do you like video games like the doom"
//                ,null,null,null,null, appContext.getApplicationContext());
//        int id = 100004;
//        db.addPostSpecifyID(test, 100004);
//        System.out.println("Founded");
//        Set<Post> result= db.postAuthorMatch("games NEAR doom");
//        Iterator<Post> iterator = result.iterator();
//        while (iterator.hasNext()){
//            Post current = iterator.next();
//            System.out.println(current.postID);
//            System.out.println(current.content);
//        }
//
//        // 测试完最好把Post删掉
//        db.deletePost(100004);
//    }
}