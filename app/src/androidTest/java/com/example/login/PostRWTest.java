package com.example.login;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.login.DataContainer.PostOld;
import com.example.login.FileIO.FileRW;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PostRWTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.login", appContext.getPackageName());
        PostOld testPost = new PostOld("test","HelloWorld");
        FileRW fileRW = new FileRW(appContext);
        String postString = testPost.toJson();
        String fileName = "test.json";
        fileRW.savingString(fileName,postString);
        String readed = "";
        try{
            readed = fileRW.readJSON("test.json");
        }catch (Exception e){
            e.printStackTrace();
        }

        assertEquals(postString,readed);
    }
}