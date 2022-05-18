package com.example.login.parserAndTokenizer;

public class TitleToken extends Token{
     String word;
     Type type;
    public TitleToken(String word){
        this.type = Type.TITLE;
        this.word = word;
    }
    @Override
    public String show() {
        return this.word;
    }

    @Override
    public int returnType() {
        return 2;
    }

    @Override
    public boolean equals(Object o){
        if (o ==null||o.getClass() != this.getClass()){
            return false;
        }else {
            TitleToken token = (TitleToken) o;
            return (this.word.equals(token.word)&&this.type == ((TitleToken) o).type);        }
    }
}
