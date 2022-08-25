package deque;

public class LinkedListDeque<Type> {
    private class Node {
        public Type item;
        public Node prev;
        public Node next;

        public Node(Type i, Node p, Node n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    private Node startSentinel;
    private Node endSentinel;
    private int size;

    /* Creates an empty linked list deque. **/
    public LinkedListDeque() {
        //Creates an empty linked list deque.
        startSentinel = new Node(null, null, null);
        endSentinel = new Node(null, null, null);
        startSentinel.next = endSentinel;
        endSentinel.prev = startSentinel;
        size = 0;
    }

    public LinkedListDeque(Type item) {
        startSentinel = new Node(null, null, null);
        endSentinel = new Node(null, null, null);
        Node n = new Node(item, startSentinel, endSentinel);
        startSentinel.next = n;
        endSentinel.prev = n;

        size = 1;
    }

    /* Adds an item of type Type to the front of the deque. You can assume that item is never null. **/
    public void addFirst(Type item) {
        //Add operations must not involve any looping or recursion.
        Node n = new Node(item, startSentinel, startSentinel.next);
        startSentinel.next = n;
        startSentinel.next.next.prev = n;
        size++;
    }

    /* Adds an item of type Type to the back of the deque. You can assume that item is never null. **/
    public void addLast(Type item) {
        //Add operations must not involve any looping or recursion.
        Node n = new Node(item, endSentinel.prev, endSentinel);
        endSentinel.prev = n;
        endSentinel.prev.prev.next = n;
        size++;
    }

    /* Returns true if deque is empty, false otherwise. **/
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque. **/
    public int size() {
        // must take constant time
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line. **/
    public void printDeque() {
        Node n = startSentinel.next;                            //first node
        while(n.next != null) {
            System.out.print(n.item.toString() + " ");
            n = n.next;
        }
        System.out.println();
    }

    /*  Removes and returns the item at the front of the deque. If no such item exists, returns null. **/
    public Type removeFirst() {
        // Remove operations may not use looping or recursion
        if(size == 0) {
            return null;
        }
        Type first = startSentinel.next.item;

        startSentinel.next = startSentinel.next.next;
        startSentinel.next.prev = startSentinel;

        size--;
        return first;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. **/
    public Type removeLast() {
        // Remove operations may not use looping or recursion
        if(size == 0) {
            return null;
        }
        Type last = endSentinel.prev.item;

        endSentinel.prev = endSentinel.prev.prev;
        endSentinel.prev.next = endSentinel;

        size--;
        return last;
    }

    /*  Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque! **/
    public Type get(int index) {
        //must use iteration
        Node n = startSentinel.next;        //first node
        int i = 0;

        while (n.next != null) {
            if (i == index) {
                return n.item;
            }
            n = n.next;
            i++;
        }
        return null;

    }

    /* Same as get, but uses recursion. **/
//    public Type getRecursive(int index) {
//          //TODO
//    }



    //TODO: after lecture 11
//    /* The Deque objects we’ll make are iterable (i.e. Iterable<Type>) so we must provide this method to return an iterator. **/
//    public Iterator<Type> iterator() {
//    }

//    /* Returns whether or not the parameter o is equal to the Deque.
//    o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic Type’s equals method)
//    in the same order.
//     */
//    public boolean equals(Object o) {
//        // uses instance of
//        return false;
//    }
}
