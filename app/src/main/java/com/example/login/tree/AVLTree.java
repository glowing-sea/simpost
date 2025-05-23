package com.example.login.tree;

import android.content.Context;

import com.example.login.FileIO.FileRW;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/**
 * Welcome! Make sure to check out 'readme.md' for a rundown of requirements/description of this implementation
 * that may differ from normal implementations. Before starting, it may also be worth checking out Tree.java
 * and BinarySearchTree.java as all method description is contained in the superclass unless edited. For
 * example: the description for 'insert' cannot be found here. It is in the superclass!
 * <p>
 * Please note that you may edit this class as much as you like (i.e.create helper methods if you want!).
 * So long as you genuinely pass the tests (i.e. do not change existing methods signatures). Ask questions if you are
 * lost or unsure.
 * You SHALL NOT edit any other classes.
 * <p>
 * Lastly, if you are looking to better visualise the results of your insertion, you are free print the contents
 * of the method '.display()' (found in Tree.java which class, AVLTree, extends through BinarySearchTree). This
 * method will provide you with a graphical representation of the tree.
 */
public class AVLTree extends BinarySearchTree {
    /*
        As a result of inheritance by using 'extends BinarySearchTree<T>,
        all class fields within BinarySearchTree are also present here.
        So while not explicitly written here, this class has:
            - value
            - leftNode
            - rightNode
     */

    public AVLTree(Integer value, String userName, String passWord) {
        super(value,userName,passWord);//refering to binarySearch tree
        // Set left and right children to be of EmptyAVL as opposed to EmptyBST.
        this.leftNode = new EmptyAVL();
        this.rightNode = new EmptyAVL();
    }

    public String getPassWord(int userId){
        if(this.userId == userId){
            return this.password;
        }else {
            if (this.userId < userId){
                return leftNode.getPassword(userId);
            }else {
                return rightNode.getPassword(userId);
            }
        }
    }


    public AVLTree(){
        this.leftNode = null;
        this.rightNode = null;
    }

    public AVLTree(Integer value,String userName,String password, Tree leftNode, Tree rightNode) {
        super(value, userName,password,leftNode, rightNode);
    }

    private String toJson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AVLTree.class, new AVLTreeAdapter());
        Gson tson = builder.create();

        return tson.toJson(this);
    }

    public void save(Context context){
        FileRW fileRW = new FileRW(context);
        fileRW.savingString("AVLTree.json",this.toJson());
    }

    /**
     * @return balance factor of the current node.
     */
    public int getBalanceFactor() {
        /*
             Note:
             Calculating the balance factor and height each time they are needed is less efficient than
             simply storing the height and balance factor as fields within each tree node (as some
             implementations of the AVLTree do). However, although it is inefficient, it is easier to implement.
         */
        return leftNode.getHeight() - rightNode.getHeight();
    }

    @Override
    public AVLTree insert(Integer element, String userName, String passWord) {
        /*
            TODO: Write and or complete your insertion code here
            Note that what each method does is described in its superclass unless edited.
            E.g. what 'insert' does is described in Tree.java.
         */
        //System.out.println(element);
        AVLTree Result = new AVLTree(this.userId,this.userName,this.password,this.leftNode,this.rightNode);
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        //after comparing the value, it is inserted in to tree on a recursive manner
        //after each insert the balance of the tree would be checked
        if (element.compareTo(userId) > 0) {
            Result = new AVLTree(this.userId,this.userName,this.password,this.leftNode,this.rightNode.insert(element,userName,passWord));
            Result = (AVLTree) checkAndBalance(Result);
            return Result;
        } else if (element.compareTo(userId) < 0) {
            Result = new AVLTree(this.userId,this.userName,this.password,this.leftNode.insert(element,userName,passWord),this.rightNode);
            Result = (AVLTree) checkAndBalance(Result);
            return Result;
            // COMPLETE
        } else {
            Result = (AVLTree) checkAndBalance(Result);
            return Result;
        }
        // Ensure input is not null.
    }

    public AVLTree deletion(Integer element){
        AVLTree unbalance =(AVLTree) super.deletion(element,null);
        return (AVLTree) unbalance.checkAndBalance(unbalance);
    }

    /**
     * Check and balance (this is used with checkAndBalance2 and checkAndBalanceN2)
     * This function check the balance of the current node and do rotation to balance the tree
     * As this is called after every insert operation there would not be things that is abs above 2
     *
     *
     * @return the balanced tree
     */
    Tree checkAndBalance(Tree tree){
        //use try to convert to make sure that not checking on a EmptyAVL
        try{
            AVLTree nonEmpty = (AVLTree) tree;
            switch (nonEmpty.getBalanceFactor()) {
                case (1):
                case (-1):
                case (0): { //the tree do not need balance
                    return  nonEmpty;
                }
                case (2):{ //current node need a right rotate
                    //check is sub node need a left rotate and do it
                    tree.rightNode = checkAndBalance2(tree.rightNode);
                    tree.leftNode = checkAndBalance2(tree.leftNode);
                    nonEmpty = nonEmpty.rightRotate();
                    return nonEmpty;
                }
                case (-2):{//current node need a left rotate
                    //check is sub node need a right rotate and do it
                    tree.leftNode = checkAndBalanceN2(tree.leftNode);
                    tree.rightNode = checkAndBalanceN2(tree.rightNode);
                    nonEmpty = nonEmpty.leftRotate();
                    return nonEmpty;
                }
            }
        }catch (Exception e){
            return tree;
        }
        return tree;
    }

    /**
     * the nested function that is used with check and balance
     * on the situation that the balance factor is 2 if there is a -1 facto under do the 1st flip of double flip
     * @param tree children of node with balance factor  2
     * @return node after first double flip
     */
    Tree checkAndBalance2(Tree tree){
        if(tree.getClass() != EmptyAVL.class){ //we don't do check balance on an empty tree
            AVLTree nonEmpty = (AVLTree) tree;
            switch (nonEmpty.getBalanceFactor()){
                case(1):
                case(0): {//sub node do not need a rotate
                    return nonEmpty;
                }
                case(-1):{//sub node need a rotate
                    return nonEmpty.leftRotate();
                }
                default:{//Just making sure we do throw away data
                    return tree;
                }
            }
        } else {//EmptyAVL is not process and returned
            return tree;
        }
    }

    /**
     * the nested function that is used with check and balance
     * on the situation that the balance factor is -2 if there is a 1 facto under do the 1st flip of double flip
     * @param tree children of node with balance factor  -2
     * @return node after first double flip
     */
    Tree checkAndBalanceN2(Tree tree){
        if(tree.getClass() != EmptyAVL.class){ //we don't do check balance on an empty tree
            AVLTree nonEmpty = (AVLTree) tree;
            switch (nonEmpty.getBalanceFactor()){
                case(-1):
                case(0): {//sub node do not need a right rotate
                    return nonEmpty;
                }
                case(1):{//sub node need a rotate
                    return nonEmpty.rightRotate();
                }
                default:{//Just making sure we do not throw away data
                    return tree;
                }
            }
        }else {//EmptyAVL is not process and returned
            return tree;
        }
    }
    /**
     * Conducts a left rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree leftRotate() {
        /*
            TODO: Write and or complete this method so that you can conduct a left rotation on the current node.
            This can be quite difficult to get your head around. Try looking for visualisations
            of left rotate if you are confused.

            Note: if this is implemented correctly than the return MUST be an AVL tree. However, the
            rotation may move around EmptyAVL trees. So when moving trees, the type of the trees can
            be 'Tree<T>'. However, the return type should be of AVLTree<T>. To cast the return type into
            AVLTree<T> simply use: (AVLTree<T>).

            If you get an casting exception such as:
            'java.lang.ClassCastException: class AVLTree$EmptyAVL cannot be cast to class AVLTree
            (AVLTree$EmptyAVL and AVLTree are in unnamed module of loader 'app')'
            than something about your code is incorrect!
         */

        //left child ncurrent node exchange parent
        //switch ((this.leftNode.getClass() != EmptyAVL.class,this.rightNode.getClass()==EmptyAVL.class)){
        if (this.rightNode == null){
            return this;
        }else {
            AVLTree Result = (AVLTree) this.rightNode;
            this.rightNode = Result.leftNode;
            Result.leftNode = this;
            // COMPLETE
            return Result; // Change to return something different
        }
    }

    /**
     * Conducts a right rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree rightRotate() {
        /*
            TODO: Write this method so that you can conduct a right rotation on the current node.
            This can be quite difficult to get your head around. Try looking for visualisations
            of right rotate if you are confused.

            Note: if this is implemented correctly than the return MUST be an AVL tree. However, the
            rotation may move around EmptyAVL trees. So when moving trees, the type of the trees can
            be 'Tree<T>'. However, the return type should be of AVLTree<T>. To cast the return type into
            AVLTree<T> simply use: (AVLTree<T>).

            If you get an casting exception such as:
            'java.lang.ClassCastException: class AVLTree$EmptyAVL cannot be cast to class AVLTree
            (AVLTree$EmptyAVL and AVLTree are in unnamed module of loader 'app')'
            than something about your code is incorrect!
         */

        //left child ncurrent node exchange parent
        if(this.leftNode == null){
            return this;
        }else {
            AVLTree Result = (AVLTree) this.leftNode;
            this.leftNode = Result.rightNode;
            Result.rightNode = this;
            // COMPLETE
            return Result; // Change to return something different
        }

    }



    /**
     * Note that this is not within a file of its own... WHY?
     * The answer is: this is just a design decision. 'insert' here will return something specific
     * to the parent class inheriting Tree from BinarySearchTree. In this case an AVL tree.
     */
    public static class EmptyAVL extends EmptyTree {
        @Override
        public String getPassword(int userId) {
            return "null";
        }

        @Override
        public Tree insert(Integer element,String userName,String password) {
            // The creation of a new Tree, hence, return tree.
            return new AVLTree(element,userName,password);
        }

        @Override
        public Tree deletion(Integer value, Tree parent) {
            return this;
        }

        @Override
        public Integer getPreccesor(Tree parent) {
            return null;
        }

        @Override
        public boolean contain(Integer elemnet) {
            return false;
        }
    }
}
