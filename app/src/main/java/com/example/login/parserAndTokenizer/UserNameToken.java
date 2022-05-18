package com.example.login.parserAndTokenizer;

public class UserNameToken extends Token{
    String name;
    Type type;
    public UserNameToken(String name) {
        this.type = Type.USERNAME;
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if (o ==null||o.getClass() != this.getClass()){
            return false;
        }else {
            UserNameToken token = (UserNameToken) o;
            return (this.name.equals(token.name)&&this.type == ((UserNameToken) o).type);        }
    }


    @Override
    public String show() {
        return this.name;
    }

    @Override
    public int returnType() {
        return 0;
    }
}
