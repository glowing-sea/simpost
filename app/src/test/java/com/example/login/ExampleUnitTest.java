package com.example.login;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import static org.junit.Assert.*;

import com.example.login.DataContainer.Post;
import com.example.login.FileIO.FileRW;
import com.example.login.IdAssigners.UserIdAssigner;
import com.google.gson.Gson;

import javax.crypto.spec.PSource;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    UserIdAssigner  test = new UserIdAssigner(0);
    Gson gson = new Gson();
    String json = gson.toJson(test);
    String hi = "";
}