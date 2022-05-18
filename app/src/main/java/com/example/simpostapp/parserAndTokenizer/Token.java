package com.example.simpostapp.parserAndTokenizer;

public abstract class Token {
    public enum Type{
        TIME,TITLE,USERNAME,WORD
    }
    public abstract String show();

    public abstract int returnType();
}
