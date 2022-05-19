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
        Tree leftNode = null;
        Tree rightNode = null;
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
                leftNode = readNode(reader);
                if (leftNode == null){
                    leftNode = new AVLTree.EmptyAVL();
                }
                System.out.println("end leftNede");
                //has next show false at this line
            }else if(name.equals("rightNode")){
                rightNode = readNode(reader);
                if (rightNode == null){
                    rightNode = new AVLTree.EmptyAVL();
                }
            }
        }

        if (rightNode == null){
            rightNode = new AVLTree.EmptyAVL();
        }

        if (leftNode == null){
            leftNode = new AVLTree.EmptyAVL();
        }
        AVLTree rtn = new AVLTree(userId,userName,userPassword,leftNode,rightNode);

        return rtn;
    }

    public Tree readNode(JsonReader reader) throws IOException {
        Integer userId = null;
        String userName = null;
        String userPassword = null;
        Tree leftNode = null;
        Tree rightNode = null;
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return new AVLTree.EmptyAVL();
        }
        if (reader.peek() == JsonToken.BEGIN_OBJECT) {
            reader.beginObject();
        }
        if (reader.peek() == JsonToken.END_OBJECT){
            reader.endObject();
            return new AVLTree.EmptyAVL();
        }

        JsonToken peak= reader.peek();
        while (reader.peek() != JsonToken.END_OBJECT){
            String name = reader.nextName();
            System.out.println(name);
            if(name.equals("userId")){
                userId = reader.nextInt();
            }else if (name.equals("userName")){
                userName = reader.nextString();
            }else if (name.equals("password")){
                userPassword = reader.nextString();
            }else if(name.equals("leftNode")){
                leftNode =   readNode(reader);
            }else if(name.equals("rightNode")){
                rightNode =   readNode(reader);
            }
        }
        reader.endObject();

        while (reader.peek() == JsonToken.END_OBJECT){
            reader.endObject();
        }
        AVLTree rtn = new AVLTree(userId,userName,userPassword,leftNode,rightNode);
        Gson gson = new Gson();
        return rtn;
    }
}
