package com.example.login.tree;

/**
 * modified from https://gitlab.cecs.anu.edu.au/u1064702/comp2100-6442-2022.s1
 * An AVL tree is actually an extension of a Binary Search Tree
 * with self balancing properties. Hence, our AVL trees will 'extend'
 * this Binary Search tree data structure.
 */
public class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {

    public BinarySearchTree(T value,String userName,String passWord) {
        super(value,userName,passWord);
        this.leftNode = new EmptyBST<>();
        this.rightNode = new EmptyBST<>();
    }

    public BinarySearchTree(T value,String password) {
        super(value,password);
        this.leftNode = new EmptyBST<>();
        this.rightNode = new EmptyBST<>();
    }

    public BinarySearchTree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, leftNode, rightNode);
    }

    @Override
    public T min() {
        return (leftNode instanceof EmptyTree) ? userId : leftNode.min();
    }

    @Override
    public T max() {
        return (rightNode instanceof EmptyTree) ? userId : rightNode.max();
    }

    @Override
    public Tree<T> find(T element) {
        /*
            Left is less, right is greater in this implementation.
            compareTo returns 0 if both elements are equal.
            compareTo returns < 0 if the element is less than the node.
            compareTo returns > 0 if the element is greater than the node.
         */

        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (element.compareTo(userId) == 0) {
            return this;
        } else if (element.compareTo(userId) < 0) {
            return leftNode.find(element);
        } else {
            return rightNode.find(element);
        }
    }

    @Override
    public BinarySearchTree<T> insert(T element,String userName,String password) {
        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        // If the two values are equal, in this implementation we want to insert to the left.
        if (element.compareTo(userId) > 0) {
            return new BinarySearchTree<>(userId, leftNode, rightNode.insert(element,userName,password));
        } else {
            return new BinarySearchTree<>(userId, leftNode.insert(element,userName,password), rightNode);
        }
    }

    @Override
    public Tree<T> deletion(T value, Tree<T> parent) {
        if(value.compareTo(this.userId)>0){
            this.rightNode = this.rightNode.deletion(value,this);
        }else if(value.compareTo(this.userId )<0) {
            this.leftNode = this.leftNode.deletion(value,this);
        }else {
            if(this.leftNode.userId == null && this.rightNode.userId != null){
                return  this.rightNode;
            }else if(this.leftNode.userId!= null && this.rightNode.userId == null){
                return this.leftNode;
            }else {
                this.userId = this.getPreccesor(parent);
                this.leftNode = this.leftNode.deletion(value,this);
                this.rightNode = this.rightNode.deletion(value,this);
                //TODO:find the presessor
            }
        }
        return this;
    }

    @Override
    public T getPreccesor(Tree<T> parent) {
        if(this.leftNode.userId != null){
            return this.leftNode.max();
        }else {
            return parent.userId;
        }
    }

    @Override
    public boolean contain(T elemnet) {
        if(elemnet.compareTo(this.userId) > 0){
            return this.rightNode.contain(elemnet);
        }else if(elemnet.compareTo(this.userId) < 0){
            return this.leftNode.contain(elemnet);
        }else {
            return true;
        }
    }


    /**
     * Note that this is not within a file of its own... WHY?
     * The answer is: this is just a design decision. 'insert' here will return something specific
     * to the parent class inheriting Tree. In this case a BinarySearchTree.
     */
    public static class EmptyBST<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element,String userName,String passWord) {
            // The creation of a new Tree, hence, return tree.
            return new BinarySearchTree<T>(element,userName,passWord);
        }

        @Override
        public Tree<T> deletion(T value, Tree<T> parent) {
            return this;
        }

        @Override
        public T getPreccesor(Tree<T> parent) {
            return null;
        }

        @Override
        public boolean contain(T elemnet) {
            return false;
        }

    }
}
