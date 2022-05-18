package com.example.login.parserAndTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tokenizier {
    private String text;
    public Tokenizier(String input){
        this.text = input;
    }

    //tokenize a string to a list of tokens
    public List<Token> tokenize(){
        List<Token> rtn = new ArrayList<>();
        text = text.replace(' ','|');
        text = text.toLowerCase(Locale.ROOT);
        String[] words = text.split("\\|");
        int index = 0;
        while (index < words.length){
            String word = words[index];
            if (word.equals("")){//deal with empty things
                continue;
            }
            rtn.add(singlrWorld(word));
            index ++;
        }
        return rtn;
    }

    private Token singlrWorld(String word){
        char firstChar = word.charAt(0);
        char lastChar = word.charAt(word.length()-1);
        Token rtn;
        if (firstChar == '{'&&lastChar == '}'){
            rtn = new UserNameToken(word.substring(1,word.length()-1));
        }else if(firstChar == '['&&lastChar ==']') {
            rtn = new TitleToken(word.substring(1,word.length()-1));
        }else {
            rtn = new WordToken(word);
        }
        return rtn;
    }
}
