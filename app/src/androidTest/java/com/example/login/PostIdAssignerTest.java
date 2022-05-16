package com.example.login;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.IdAssigners.PostIdAssigner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class PostIdAssignerTest {
    @Test
    public void assigningID() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(0,PostIdAssigner.getInstance(appContext).assignID(appContext));
        assertEquals(1,PostIdAssigner.getInstance(appContext).assignID(appContext));
    }
    @Test
    public void savingAndReading(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(2,PostIdAssigner.getInstance(appContext).assignID(appContext));
    }
    @Test
    public void clearAssigner(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        File clearing = new File(appContext.getFilesDir() + "/IdAssigners/PostIdAssigner.json");
        clearing.delete();
    }
}
