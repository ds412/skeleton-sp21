package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Your code will not compile until you create the BSTMap class and you implement all the methods of Map61B.
 * You can implement methods one at a time by writing the method signatures of all the required methods,
 * but throwing UnsupportedOperationExceptions for the implementations, until you get around to actually writing them.
 *
 * Your BSTMap should also add an additional method printInOrder() (not given in the Map61B interface) that prints out your BSTMap
 * in order of increasing Key. We will not test the result of this method, but you will find this helpful for testing your implementation!
 *
 * In your implementation you should assume that generic keys K in BSTMap<K,V> extend Comparable.
 * In other words, you can assume that generic keys K have a compareTo method.
 * This can be enforced in Java with a bounded type parameter.
 *
 * We also recommend that you use a private nested BSTNode class to help facilitate your implementation.
 * How you design and use this inner class is up to you!
 * You can test your implementation using TestBSTMap.java.
 */

public class BSTMap<K extends Comparable, V> implements Map61B<K, V>{
    private class BSTNode{
        private K key;
        private V value;
        private BSTNode left, right;                //left and right children of this node
        private int size;                           //number of nodes in the subtree

        public BSTNode(K key, V value, int size){
            this.key = key;
            this.value = value;
            this.size = size;                       //number of nodes in the subtree
        }
    }

    private BSTNode root;                           //root of this BST

    //initialize an empty BSTMap
    public BSTMap(){
    }

    //return number of K-V pairs in this BST
    @Override
    public int size() {                     //to get the size of the BST, get the size of the root node
        return size(root);
    }

    public int size(BSTNode x){             //get the size of a node
        if (x == null){
            return 0;
        }
        else{
            return x.size;
        }
    }

    @Override
    public void put(K key, V value) {
        if(key == null){
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = insert(root, key, value);                   //put this in the tree, starting at the root
    }

    //private method which returns a new BSTNode
    private BSTNode insert(BSTNode x, K key, V value){
        if(x == null){                                  //if no root exists yet, this becomes the root
            return new BSTNode(key, value, 1);
        }
        int compare = key.compareTo(x.key);             //make a comparator
        if (compare < 0){                               //if this node is smaller than the root, put it to the left
            x.left = insert(x.left, key, value);
        }
        else if (compare > 0){                          //if this node is larger than the root, put it to the right
            x.right = insert(x.right, key, value);
        }
        else{                                           //if it's the same key, update value
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right);      //update size
        return x;
    }

    //Gets the value stored under a certain key
    @Override
    public V get(K key) {
        return get(root, key);                  //use get helper method, starting at the root
    }

    private V get(BSTNode x, K key){
        if(key == null){
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if(x == null){
            return null;
        }
        int compare = key.compareTo(x.key);
        if(compare < 0){                        //it's smaller, so look left
            return get(x.left, key);
        }
        else if (compare > 0) {                 //it's larger, so look right
            return get(x.right, key);
        }
        else {                                  //found it, return the value
            return x.value;
        }
    }

    //Checks whether this BST contains this key
    @Override
    public boolean containsKey(K key) {
        if(key == null){
            return false;
        }
        return get(key) != null;                            //if get doesn't return null for this key, it's in the BST
    }

    @Override
    public void clear() {                                   //clears the BST by setting the root to null
        root = null;
    }


    //OPTIONAL OK
    @Override
    public Set keySet() {
        if(root == null){
            return null;
        }
        else {
            Set<K> set = new TreeSet<>();
            keySet(root, set);
            return set;
        }
    }

    private void keySet(BSTNode node, Set set){
        if(node.right == null && node.left == null){           //dead end
            set.add(node.key);
        }
        else if(node.right == null && node.left != null){      //go left
            keySet(node.left, set);
            set.add(node.key);
        }
        else if(node.right != null && node.left == null){    //go right
            keySet(node.right, set);
            set.add(node.key);
        }
        else {                                              //go both ways
            keySet(node.left, set);
            set.add(node.key);
            keySet(node.right, set);
        }
    }

    //prints out your BSTMap in order of increasing Key
    public void printInOrder(){
        if(root == null){
            System.out.println("Empty");
        }
        else {
            printInOrder(root);
        }
    }

    private void printInOrder(BSTNode node){
        if(node.right == null && node.left == null){
            System.out.println(" "+ node.key.toString() + " ");
        }
        else if(node.right == null && node.left != null){
            printInOrder(node.left);
            System.out.println(" "+ node.key.toString() + " ");
        }
        else if(node.right != null && node.left == null){
            printInOrder(node.right);
            System.out.println(" " + node.key.toString() + " ");
        }
        else {
            printInOrder(node.left);
            System.out.println(" " + node.key.toString() + " ");
            printInOrder(node.right);
        }
    }

    //will stay unsupported (OPTIONAL)
    @Override
    public V remove (K key) throws UnsupportedOperationException {
        return null;
    }

    //will stay unsupported (OPTIONAL)
    @Override
    public V remove(K key, V value) throws UnsupportedOperationException {
        return null;
    }

    //will stay unsupported (OPTIONAL)
    @Override
    public Iterator iterator () throws UnsupportedOperationException {
        return null;
    }
}
