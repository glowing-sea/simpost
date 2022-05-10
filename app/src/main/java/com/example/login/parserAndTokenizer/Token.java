package com.example.login.parserAndTokenizer;

public abstract class Token {
    public enum Type{
        TIME,TITLE,USERNAME,WORD
    }
    public abstract String show();

}
