package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p, Node n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    private Node startSentinel;
    private Node endSentinel;
    private int size;

    /* Creates an empty linked list deque, using a circular sentinel topology **/
    public LinkedListDeque() {
        startSentinel = new Node(null, null, null);
        endSentinel = new Node(null, null, null);
        startSentinel.next = endSentinel;
        startSentinel.prev = endSentinel;
        endSentinel.prev = startSentinel;
        endSentinel.next = startSentinel;
        size = 0;
    }

    /* Creates a one-item linked list deque, using a circular sentinel topology **/
    public LinkedListDeque(T item) {
        startSentinel = new Node(null, null, null);
        endSentinel = new Node(null, null, null);
        Node n = new Node(item, startSentinel, endSentinel);
        startSentinel.next = n;
        startSentinel.prev = endSentinel;
        endSentinel.prev = n;
        endSentinel.next = startSentinel;

        size = 1;
    }

    /* Adds an item of type T to the front of the deque. You can assume that item is never null. **/
    @Override
    public void addFirst(T item) {
        //Add operations must not involve any looping or recursion.
        Node n = new Node(item, startSentinel, startSentinel.next);
        startSentinel.next = n;
        startSentinel.next.next.prev = n;
        size++;
    }

    /* Adds an item of type T to the back of the deque. You can assume that item is never null. **/
    @Override
    public void addLast(T item) {
        //Add operations must not involve any looping or recursion.
        Node n = new Node(item, endSentinel.prev, endSentinel);
        endSentinel.prev = n;
        endSentinel.prev.prev.next = n;
        size++;
    }

    /* Returns true if deque is empty, false otherwise. **/
//    @Override
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /* Returns the number of items in the deque. **/
    @Override
    public int size() {
        // must take constant time
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line. **/
    @Override
    public void printDeque() {
        Node n = startSentinel.next;                            //first node
        while(n.next != startSentinel) {                        //until we get to endSentinel (circular topology)
            System.out.print(n.item.toString() + " ");
            n = n.next;
        }
        System.out.println();
    }

    /*  Removes and returns the item at the front of the deque. If no such item exists, returns null. **/
    @Override
    public T removeFirst() {
        // Remove operations may not use looping or recursion
        if(size == 0) {
            return null;
        }
        T first = startSentinel.next.item;

        startSentinel.next = startSentinel.next.next;
        startSentinel.next.prev = startSentinel;

        size--;
        return first;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. **/
    @Override
    public T removeLast() {
        // Remove operations may not use looping or recursion
        if(size == 0) {
            return null;
        }
        T last = endSentinel.prev.item;

        endSentinel.prev = endSentinel.prev.prev;
        endSentinel.prev.next = endSentinel;

        size--;
        return last;
    }

    /*  Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque! **/
    @Override
    public T get(int index) {
        //must use iteration
        Node n = startSentinel.next;        //first node
        int i = 0;

        while (n.next != startSentinel) {   //until we get to the endSentinel (circular topology)
            if (i == index) {
                return n.item;
            }
            n = n.next;
            i++;
        }
        return null;

    }

    /* Same as get, but uses recursion. **/
    public T getRecursive(int index) {
        return getRecursive(index, startSentinel.next);             //use helper function, starting at first item
    }

    private T getRecursive(int index, Node n){
        if (n == endSentinel) {                                    //if we reach the endSentinel before reaching the index
            return null;
        }
        else if (index == 0) {                                     //base case: if we reach the node, return the item
            return n.item;
        }
        return getRecursive(index - 1, n.next);             //recursive case: keep moving, reducing the index at each step
    }



    /* The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator. **/
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{
        private Node position;

        public LinkedListIterator(){
            position = startSentinel;
        }

        @Override
        public boolean hasNext() {
            return position.next != startSentinel;
        }

        @Override
        public T next() {
            return position.next.item;
        }
    }

    /* Returns whether the parameter o is equal to the Deque.
    o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method)
    in the same order.
     */
    public boolean equals(Object o) {
        if (this == o) {                                //if it's the exact same object, return true
            return true;
        }
        if (o == null) {                                //if other is null, return false
            return false;
        }
        if (o.getClass() != this.getClass()) {          //if different class, return false
            return false;
        }
        LinkedListDeque <T> other = (LinkedListDeque<T>) o;
        if (other.size() != this.size()) {             //if different sizes, return false
            return false;
        }

        Node n = this.startSentinel.next;                       //first node
        Node p = other.startSentinel.next;
        if (n.item.getClass() != p.item.getClass()) {           //if items of different types, return false
            return false;
        }
        while(n.next != startSentinel) {                        //until we get to endSentinel (circular topology)
            if (n.next != p.next) {                             //if other lacks items which are in this, return false
                return false;
            }
            n = n.next;
            p = p.next;
        }
        return true;
    }
}
