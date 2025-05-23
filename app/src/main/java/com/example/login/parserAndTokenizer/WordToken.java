package com.example.login.parserAndTokenizer;

public class WordToken extends Token{
    Type type;
    String word;

    public WordToken(String word){
        this.word = word;
        this.type = Type.WORD;
    }

    @Override
    public String show() {
        return this.word;
    }

    @Override
    public int returnType() {
        return 3;
    }

    @Override
    public boolean equals(Object o){
        if (o ==null||o.getClass() != this.getClass()){
            return false;
        }else {
            WordToken token = (WordToken) o;
            return (this.word.equals(token.word)&&this.type == ((WordToken) o).type);
        }
    }
}
