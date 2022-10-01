package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INIT_CAPACITY = 16;
    private static final double INIT_LOADFACTOR = .75;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    //Node is a private helper class we give that stores a single key-value mapping (no modification needed)
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

        private K getKey(){
            return key;
        }

        private V getValue(){
            return value;
        }
    }

    /* Instance Variables */
    //buckets is a private variable which is an array (or table) of Collection<Node> objects,
    //with each Collection of Nodes representing a single bucket in the hash table
    private Collection<Node>[] buckets;
    // TODO: You should probably define some more!

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }
    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
//        Collection<Node>[] table = new Collection<>[tableSize];
//        for(int i = 0; i < tableSize; i++){
//            table[i] = createBucket();
//        }
//        return table;
        return null;
    }

    /** private variables */
    private int size;                            //number of KV pairs in the hashTable
    private int capacity;                        //max capacity of the hashTable
    private double loadFactor;                  //load factor

    /** Constructors */
    //Empty constructor
    public MyHashMap() {
        this(INIT_CAPACITY, INIT_LOADFACTOR);
    }

    //Constructor with number of buckets equal to initialSize (DEFAULT = 16)
    public MyHashMap(int initialSize) {
        this(initialSize, INIT_LOADFACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    //TODO: constructor with number of buckets equal to initialSize and loadFactor (DEFAULT: size = 16, LF = 0.75)
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = 0;
        this.capacity = initialSize;
        this.loadFactor = maxLoad;
        buckets = (Collection<Node>[]) new Collection[capacity];
        for(int i = 0; i < capacity; i++){
            buckets[i] = createBucket();
        }
    }

    private int hashCode(K key){
        if(key ==null) {
            return 0;
        }
        else {
            return (key.hashCode() & 0x7fffffff) % capacity;            //as a hash function, using hex to make 32-bit quantity unsigned
        }
    }


    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    //TODO:
    // resize the hashTable to have the given number of buckets, rehashing all of the keys
    // when resizing, make sure to do so multiplicatively
    private void resize(int numberOfBuckets){

    }

    @Override
    public void clear() {
        this.capacity = INIT_CAPACITY;
        buckets = (Collection<Node>[]) new Collection[capacity];
        for (int i = 0; i < capacity; i++){
            buckets[i] = createBucket();
        }
        this.size = 0;
    }

    //private helper method to find the Node for a given key
    private Node find(K key) {
        int hashcode = hashCode(key);                           //get the hashcode for this key
        Collection<Node> bucket = buckets[hashcode];            //get the bucket for this hashcode

        for (Node node : bucket) {                               //go through the nodes of the bucket
            if (node.getKey().equals(key)) {                     //if the node has the right key
                return node;                                    //return the node
            }
        }
        return null;                                            //else, return null
    }

    // if the same key is inserted more than once, the value should be updated each time
    @Override
    public void put(K key, V value) {
        int hashcode = hashCode(key);                           //get the hashcode for this key
        Collection<Node> bucket = buckets[hashcode];            //get the bucket for this hashcode

        for (Node node:bucket){                                 //go through the nodes of the bucket
            if (node.getKey().equals(key)){                     //if the key is already there
                node.value = value;                             //update the value
                return;
            }
        }
        bucket.add(createNode(key, value));                     //else, create a new node for this KV pair
        size += 1;                                              //and update the size
    }


    // Iterate over the Collection, and find the Node whose key is .equal() to the key you are searching for
    @Override
    public boolean containsKey(K key) {
        return find(key) != null;                                //return whether the key is found
    }

    // iterate over the Collection, and find the Node whose key is .equal() to the key you are searching for
    @Override
    public V get(K key) {
            Node node = find(key);
            if(node != null){
                return node.getValue();
            }
            return null;
    }

    @Override
    public int size() {
        return this.size;
    }


    //may return keys in any order
    @Override
    public Set<K> keySet() {
        Set<K> kSet = new HashSet<>();
        for (Collection<Node> bucket: buckets){
            for(Node node: bucket) {
                kSet.add(node.getKey());
            }
        }
        return kSet;
    }

    //TODO
    //returns an Iterator that iterates over the stored keys (may return keys in an any order)
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    //OPTIONAL
    @Override
    public V remove(K key) throws UnsupportedOperationException {
        return null;
    }

    //OPTIONAL
    @Override
    public V remove(K key, V value) throws UnsupportedOperationException {
        return null;
    }

}
