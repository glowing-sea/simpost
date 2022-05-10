package com.example.login.parserAndTokenizer;

public class TimeToken extends Token{
    String time;
    Type type;
    public TimeToken(String time){
        this.time = time;
        this.type = Type.TIME;
    }
    @Override
    public String show() {
        return this.time + " ";
    }

    @Override
    public boolean equals(Object o){
        if (o ==null||o.getClass() != this.getClass()){
            return false;
        }else {
            TimeToken token = (TimeToken) o;
            return (this.time.equals(token.time)&&this.type == ((TimeToken) o).type);        }
    }
}
