package com.example.login.parserAndTokenizer;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private String inputText ="";
    public Parser(String inputText){
        this.inputText = inputText;
    }
    public String parsedString(){
        Tokenizier tokenizier = new Tokenizier(this.inputText);
        List<Token> tokensContained = tokenizier.tokenize();

        //catorgise the tokens we have for optimising search
        List<Token> userNamtToken = new ArrayList<>();
        List<Token> titleToken = new ArrayList<>();
        List<Token> wordToken = new ArrayList<>();
        //debuging code
        String debugString = "";
        int index = 0;
        while(index < tokensContained.size()){
            debugString += tokensContained.get(index).show();
            debugString += "|";
            int type = tokensContained.get(index).returnType();
            if (type == 0){
                userNamtToken.add(tokensContained.get(index));
            }else if(type == 2){
                titleToken.add(tokensContained.get(index));
            }else {
                wordToken.add(tokensContained.get(index));
            }
            index ++;
        }

        StringBuilder creatorMatch = new StringBuilder("");
        if (userNamtToken.size() != 0) {
            creatorMatch.append("creator:");
            index = 0;
            while (index < userNamtToken.size()) {
                if (index == 0) {
                    creatorMatch.append(userNamtToken.get(index).show());
                    index++;
                    continue;
                }
                creatorMatch.append(" ");
                creatorMatch.append(userNamtToken.get(index).show());
                index++;
            }
        }


        StringBuilder titleMatch = new StringBuilder();
        if (titleToken.size() != 0) {
            titleMatch.append(" title:");
            index = 0;
            while (index < titleToken.size()) {
                if (index == 0) {
                    titleMatch.append(titleToken.get(index).show());
                    index++;
                    continue;
                }
                titleMatch.append(" ");
                titleMatch.append(titleToken.get(index).show());
                index++;
            }
            titleMatch.append(" ");
        }
        StringBuilder contentMatch = new StringBuilder();
        if (wordToken.size()!= 0){
            index = 0;
            contentMatch.append(" content:");
            while (index < wordToken.size()){
                if (index == 0){
                    contentMatch.append(wordToken.get(index).show());
                    index ++;
                    continue;
                }
                contentMatch.append(" ");
                contentMatch.append(wordToken.get(index).show());
                index ++;
            }
        }
        return "" + creatorMatch + contentMatch + titleMatch;
    }



}
