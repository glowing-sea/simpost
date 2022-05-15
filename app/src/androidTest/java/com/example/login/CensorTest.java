package com.example.login;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.login.Database.HelperMethods;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CensorTest {
    String test1 = "fuck you";
    String test2 = "oh shit ";
    @Test public void censorTest1(){
        String expected1 = "**** you";
        assertEquals(HelperMethods.getCensored(test1), expected1);
    }
    @Test public void censorTest2(){
        String expected2 = "oh **** ";
        assertEquals(HelperMethods.getCensored(test2), expected2);
    }
}
