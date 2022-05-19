package com.example.login.tree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.InetAddress;
import java.security.AlgorithmConstraints;
import java.util.zip.GZIPOutputStream;

public class AVLTreeAdapter extends TypeAdapter<Tree> {


    @Override
    public void write(JsonWriter out, Tree value) throws IOException {
        Gson gson = new Gson();
        String result = gson.toJson(value);
        out.value(result);
    }

    @Override
    public Tree read(JsonReader reader) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AVLTree.class, new AVLTreeAdapter());
        Gson tson = builder.create();

        Integer userId = 0;
        String userName = "";
        String userPassword = "";
        AVLTree leftNode = null;
        AVLTree rightNode = null;
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return new AVLTree.EmptyAVL();
        }

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            System.out.println(name);
            if(name.equals("userId")){
                userId = reader.nextInt();
            }else if (name.equals("userName")){
                userName = reader.nextString();
            }else if (name.equals("password")){
                userPassword = reader.nextString();
            }else if(name.equals("leftNode")){
                String leftNodeString = reader.nextString();
                leftNode = tson.fromJson(leftNodeString,AVLTree.class);
            }else if(name.equals("rightNode")){
                String rightNodeString = reader.nextString();
                rightNode = tson.fromJson(rightNodeString,AVLTree.class);
            }
        }
        AVLTree rtn = new AVLTree(userId,userName,userPassword,leftNode,rightNode);
        Gson gson = new Gson();
        return null;
    }
}
