package com.example.login;

import com.example.login.tree.AVLTree;
import com.example.login.tree.AVLTreeAdapter;
import com.example.login.tree.Tree;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

public class avlSerialisationTest {
    @Test
    public void serilise(){
        Gson gson = new Gson();
        AVLTree tree = new AVLTree(0,"Dai","passwor3d");

        System.out.println(gson.toJson(tree));

        tree = tree.insert(1,"daniel","passw1rd");
        System.out.println();
        String inserted = gson.toJson(tree);
        System.out.println(inserted);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AVLTree.class, new AVLTreeAdapter());
        Gson tson = builder.create();

        AVLTree back = tson.fromJson(inserted,AVLTree.class);


    }
}
