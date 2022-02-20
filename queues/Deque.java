/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (isEmpty()) {
            first = node;
            last = node;
        }
        else {
            Node oldFirst = first;
            first = node;

            oldFirst.previous = node;
            first.next = oldFirst;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (isEmpty()) {
            first = node;
            last = node;
        }
        else {
            Node oldLast = last;
            last = node;

            oldLast.next = last;
            last.previous = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldFirst = first;

        if (size > 1) {
            first = first.next;
            first.previous = null;
        }
        else {
            first = null;
            last = null;
        }

        size--;

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = last;

        if (size > 1) {
            last = last.previous;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }

        size--;

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Double> deque = new Deque<>();

        StdOut.println(deque.size);

        deque.addFirst(1.0);

        StdOut.println(deque.size);

        StdOut.println(deque.removeFirst());

        StdOut.println(deque.size);

        deque.addFirst(1.0);
        deque.addFirst(1.0);

        deque.addLast(2.0);
        deque.addLast(2.0);

        StdOut.println(deque.size);

        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());

        StdOut.println(deque.size);
    }
}
