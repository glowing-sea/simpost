package com.example.login;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.login.IdAssigners.UserIdAssigner;

import org.junit.Test;

import java.io.File;

public class UserIdAssignerTest {
    @Test
    public void assigningID() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(0, UserIdAssigner.getInstance(appContext).assignID(appContext));
        assertEquals(1,UserIdAssigner.getInstance(appContext).assignID(appContext));
    }
    @Test
    public void savingAndReading(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(2,UserIdAssigner.getInstance(appContext).assignID(appContext));
    }
    @Test
    public void clearAssigner(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        File clearing = new File(appContext.getFilesDir() + "/IdAssigners/UserIdAssigner.json");
        clearing.delete();
    }
}
