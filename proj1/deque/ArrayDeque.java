package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final int RFACTOR = 3;                      //uses rescaling factor 3, what number to use to optimize?

    /** Creates an empty ArrayDeque, of starting size 8 */
    public ArrayDeque() {
        items = (T[]) new Object[8];              //use this syntax because java doesn't allow creation of arrays of generic objects
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Adds an item of type T at the front, can be assumed not to be null */
    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        size++;
        if (nextFirst == 0){
            nextFirst = items.length;
        }
        nextFirst--;
    }

    /** Adds an item of type T at the back, can be assumed not to be null */
    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        size++;
        nextLast++;
        if(nextLast == items.length){
            nextLast = 0;
        }
    }

    /** Returns true if deque is empty, false otherwise. */
//    @Override
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.
       Once all the items have been printed, print out a new line. */
    @Override
    public void printDeque() {
        for(int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        T item = get(0);
        if (item != null) {
            size--;
            if (nextFirst != items.length - 1){
                 items[nextFirst + 1] = null;
                 nextFirst++;
            }
            else {
                items[0] = null;
                nextFirst = 0;
            }
        }
        return item;
    }


    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast() {
        T item = get(size - 1);
        if (item != null) {
            size--;
            if (nextLast != 0){
                items[nextLast - 1] = null;
                nextLast--;
            }
            else {
                items[items.length - 1] = null;
                nextLast = items.length - 1;
            }
        }
        return item;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
       If no such item exists, returns null. Must not alter the deque! */
    @Override
    public T get(int index) {
        if (index >= size) {                //can't exist if index larger or equal to size
            return null;
        }
        int location = nextFirst + 1 + index;           //location shifts to last first plus the index
        if (location >= items.length) {                 //if this goes beyond array bounds
            location = location - items.length;         //shifts it to the front
        }
        return items[location];                         //return this item
    }

    /* The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator. **/
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T>{
        private int position;

        public ArrayDequeIterator() {
            if (nextFirst != 0){
                position = nextFirst - 1;
            }
            else{
                position = size - 1;
            }
        }

        @Override
        public boolean hasNext() {
            return items[nextFirst] != null;
        }

        @Override
        public T next() {
            T returnItem = items[nextFirst];
            if (position + 1 < size) {
                position += 1;
            }
            else{
                position = 0;
            }
            return returnItem;
        }
    }

    /* Returns whether or not the parameter o is equal to the Deque.
    o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method)
    in the same order.
     */
    public boolean equals(Object o) {
        // uses instance of
        if (this == o) {                                //if it's the exact same object, return true
            return true;
        }
        if (o == null) {                                //if other is null, return false
            return false;
        }
        if (o.getClass() != this.getClass()) {          //if different class, return false
            return false;
        }
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (other.size() != this.size()) {              //if different sizes, return false
            return false;
        }

        for (T item1: this) {                               //if other lacks items which are in this, return false
            for(T item2: other){
                if (this != other){
                    return false;
                }
            }
        }
        return true;
    }


    //TODO: HELPER FUNCTIONS BELOW (NOT ADAPTED);
//    /** HELPER: Returns the item from the back of the deque. */
//    private T getLast() {
//        if (nextLast != 0){
//            return items[nextLast - 1];
//        }
//        else {
//            return items[items.length - 1];
//        }
//
//    }
//    /** HELPER: Returns the item from the front of the deque */
//    private T getFirst() {
//        if (nextFirst != items.length - 1){
//            return items[nextFirst + 1];
//        }
//        else {
//            return items[0];
//        }
//    }
//
//    /** HELPER: Resizes the underlying array to the target capacity. */
//    private void resize(int capacity) {
//        T[] resized = (T[]) new Object[capacity];
//        System.arraycopy(items, 0, resized, 0, capacity);
//        items = resized;
//    }
//
//    /** HELPER: Inserts item into given position */
//    private void insert(T x, int position) {
//        T[] newItems = (T[]) new Object[items.length + 1];
//
//        System.arraycopy(items, 0, newItems, 0, position);
//        newItems[position] = x;
//
//        System.arraycopy(items, position, newItems, position + 1, items.length - position);
//        items = newItems;
//    }

}
