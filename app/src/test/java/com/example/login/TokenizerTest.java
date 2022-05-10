package com.example.login;

import com.example.login.parserAndTokenizer.TitleToken;
import com.example.login.parserAndTokenizer.Token;
import com.example.login.parserAndTokenizer.Tokenizier;
import com.example.login.parserAndTokenizer.UserNameToken;
import com.example.login.parserAndTokenizer.WordToken;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TokenizerTest {

    @Test
    public void testTokenEqual(){
        Token t1 = new WordToken("Hi");
        Token t2 = new WordToken("World");
        Token t3 = new WordToken("Hi");
        Token t4 = new UserNameToken("Hi");
        Token t5 = new UserNameToken("Hi");
        Assert.assertEquals(false,t1.equals(t2));
        Assert.assertEquals(t1, t3);
        Assert.assertEquals(false,t1.equals(t4));
        Assert.assertEquals(true,t4.equals(t5));
    }

    @Test
    public void simple(){
        String testSentence = "Hello World";
        Tokenizier tokenizier = new Tokenizier(testSentence);
        List<Token> expected = new ArrayList<>();
        expected.add(new WordToken("hello"));
        expected.add(new WordToken("world"));
        List<Token> actuall = tokenizier.tokenize();
        int index = 0;
        while (index < actuall.size()){
            Assert.assertTrue(expected.get(index).equals(actuall.get(index)));
            index ++;
        }
    }

    @Test
    public void testIgnoring(){
        String testSentence = "Hello world,.<>?/!@#$%^&*_+=-";
        Tokenizier tokenizier = new Tokenizier(testSentence);
        List<Token> expected = new ArrayList<>();
        expected.add(new WordToken("hello"));
        expected.add(new WordToken("world"));
        List<Token> actuall = tokenizier.tokenize();
        System.out.println(expected.size());
        Assert.assertEquals(expected.size(),actuall.size());
        int index = 0;
        while (index < actuall.size()){
            System.out.println(expected.get(index));
            System.out.println(actuall.get(index));
            Assert.assertTrue(expected.get(index).equals(actuall.get(index)));
            index ++;
        }
        Assert.assertEquals(1,1);
    }

    @Test
    public void testTitle(){
        String testSentence = "[Hi]";
        Tokenizier tokenizier = new Tokenizier(testSentence);
        List<Token> actuall = tokenizier.tokenize();
        List<Token> expected = new ArrayList<>();
        expected.add(new TitleToken("hi"));
        int index = 0;
        while (index < actuall.size()){
            System.out.println(expected.get(index));
            System.out.println(actuall.get(index));
            Assert.assertEquals(true,expected.get(index).equals(actuall.get(index)));
            index ++;
        }
        Assert.assertEquals(1,1);
    }

    @Test
    public void testUser(){
        String testSentence = "{Hi}";
        Tokenizier tokenizier = new Tokenizier(testSentence);
        List<Token> actuall = tokenizier.tokenize();
        List<Token> expected = new ArrayList<>();
        expected.add(new UserNameToken("hi"));
        int index = 0;
        while (index < actuall.size()){
            System.out.println(expected.get(index));
            System.out.println(actuall.get(index));
            Assert.assertEquals(true,expected.get(index).equals(actuall.get(index)));
            index ++;
        }
        Assert.assertEquals(1,1);
    }
}
