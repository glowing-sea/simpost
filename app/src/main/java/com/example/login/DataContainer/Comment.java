package com.example.login.DataContainer;
import java.util.ArrayList;


public class Comment {
    public final String username;
    public final String content;
    public final String date;

    // Need to check if the comment is valid before using this constructor.
    public Comment (String username, String content, String date){
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public Comment (String encode){
        String[] result = encode.split("`");
        this.username = result[0];
        this.content = result[1];
        this.date = result[2];
    }

    @Override
    public String toString(){
        return username + '`' + content + '`' + date;
    }


    // Encode a list of comments
    public static String commentsEncode (ArrayList<Comment> comments){
        StringBuilder stringBuilder= new StringBuilder();
        for (Comment comment : comments){
            stringBuilder.append(comment);
            stringBuilder.append('~');
        }
        return stringBuilder.toString();
    }
    // Decode a list of comments
    public static ArrayList<Comment> commentsDecode (String string){
        ArrayList<Comment> commentsList = new ArrayList<>();
        if (string.isEmpty())
            return commentsList;
        String[] commentStringsArray = string.split("~");
        for (String commentString : commentStringsArray){
            Comment comment = new Comment(commentString);
            commentsList.add(comment);
        }
        return commentsList;
    }
}
