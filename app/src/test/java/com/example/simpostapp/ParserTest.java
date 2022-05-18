package com.example.simpostapp;

import com.example.simpostapp.parserAndTokenizer.Parser;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    @Test
    public void parseSimple(){
        Parser parser = new Parser("hello");
        Assert.assertEquals(" content:hello",parser.parsedString());
    }

    @Test
    public void parseSimple1(){
        Parser parser = new Parser("hello world");
        Assert.assertEquals(" content:hello world",parser.parsedString());
    }

    @Test
    public void parseTitle1(){
        Parser parser = new Parser("[Test]");
        Assert.assertEquals(" title:test ",parser.parsedString());
    }

    @Test
    public void parseTitle2(){
        Parser parser = new Parser("[Test] [post]");
        Assert.assertEquals(" title:test post ",parser.parsedString());
    }

    @Test
    public void parseTcreaotr1(){
        Parser parser = new Parser("{Dai}");
        Assert.assertEquals("creator:dai  ",parser.parsedString());
    }

    @Test
    public void parseAll1(){
        Parser parser = new Parser("the doom [Review] {Dai}");
        Assert.assertEquals("creator:dai content:the doom title:review ",parser.parsedString());
    }
}
