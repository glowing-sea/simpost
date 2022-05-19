package com.example.login.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Modified form:https://gitlab.cecs.anu.edu.au/u1064702/comp2100-6442-2022.s1
 * The following interface defines required methods of any Tree.
 * Note that this is simplified for this lab (no delete).
 *
 * @param <T> the generic type this Tree uses. It extends comparable
 *            which allows us to order two of the same type.
 */
public abstract class Tree {
    /**
     * Here we store our class fields.
     */
    public Integer userId;
    public String password;// element stored in this node of the tree.
    public String userName;
    public Tree leftNode;    // less than the node.
    public Tree rightNode;   // greater than the node.
    public Tree parent;
    /**
     * Constructor to allow for empty trees
     */
    public Tree() {
        userId = null;
    }

    /**
     * Constructor for creating a new child node.
     * Note that the left and right nodes must be set by the subclass.
     *
     * @param value to set as this node's value.
     */
    public Tree(Integer value,String userName,String password) {
        // Ensure input is not null.
        if (value == null)
            throw new IllegalArgumentException("Input cannot be null");

        this.userId = value;
        this.userName = userName;
        this.password = password;
    }

    public Tree(Integer value,String password) {
        // Ensure input is not null.
        if (value == null||password == null)
            throw new IllegalArgumentException("Input cannot be null");

        this.userId = value;
        this.password = password;
    }

    /**
     * Constructor for creating a new node.
     * Note that this is what allows our implementation to be immutable.
     *
     * @param value     to set as this node's value.
     * @param leftNode  left child of current node.
     * @param rightNode right child of current node.
     */
    public Tree(Integer value,String userName,String password, Tree leftNode, Tree rightNode) {
        // Ensure inputs are not null.
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");

        this.userId = value;
        this.userName = userName;
        this.password = password;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public abstract Integer min();                     // Finds the minimum.

    public abstract Integer max();                     // Finds the maximum.

    public abstract Tree find(Integer element);     // Finds the element and returns the node.

    public abstract Tree insert(Integer element,String userName,String password);   // Inserts the element and returns a new instance of itself with the new node.

    /**
     * Height of current node.
     * @return The maximum height of either children.
     */
    public int getHeight() {
        // Check whether leftNode or rightNode are EmptyTree
        int leftNodeHeight = leftNode instanceof EmptyTree ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyTree ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + userId +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }

    /**
     * Graphically visualises the tree for human readability.
     * Feel free to edit this display methods
     *
     * @return graph of the tree.
     */
    public String display() {
        return display(0);
    }

    /**
     * Graphically visualises the tree for human readability.
     * Feel free to edit this display methods
     *
     * @param tabs from the left side of the screen.
     * @return graph of the tree.
     */
    public String display(int tabs) {
        // StringBuilder is faster than using string concatenation (which in java makes a new object per concatenation).
        assert userId != null;
        StringBuilder sb = new StringBuilder(userId.toString());

        sb.append("\n").append(new String(new char[tabs]).replace("\0", "\t")).append("├─").append(leftNode.display(tabs + 1));
        sb.append("\n").append(new String(new char[tabs]).replace("\0", "\t")).append("├─").append(rightNode.display(tabs + 1));
        return sb.toString();
    }

    /**
      * List the elements of the tree with in-order
      */
    public List<Integer> inOrder() {
		return this.treeToListInOrder(this);
	}

    /**
     * Converts tree to list in-order. Helper method of inOrder.
     * @param tree to convert to list.
     * @return in-order list of tree values.
     */
	private List<Integer> treeToListInOrder(Tree tree) {
		List<Integer> list = new LinkedList<>();

		// Recurse through left subtree.
        if (tree.leftNode != null) {
            if (tree.leftNode.userId != null) {
                list.addAll(treeToListInOrder(tree.leftNode));
            }
        }


		// Add current node's value
        if (tree.userId != null) {
            list.add(tree.userId);
        }

        // Recurse through left subtree.
        if (tree.rightNode != null) {
            if (tree.rightNode.userId != null) {
                list.addAll(treeToListInOrder(tree.rightNode));
            }
        }

		return list;
	}

    public abstract Tree deletion(Integer value,Tree parent);
	public abstract Integer  getPreccesor(Tree parent);
	public abstract boolean contain(Integer elemnet);

}
