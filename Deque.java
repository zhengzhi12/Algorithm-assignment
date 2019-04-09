package com.zz.algorithm.weekTwo;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item value;
        private Node next;
        private Node prev;
        Node(Item value) {
            this.value = value;
        }
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        addValidator(item);
        Node node = new Node(item);
        node.value = item;
        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            Node oldFirst = first;  //Note here for further reference studies
            first = node;
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    public void addLast(Item item) {
        addValidator(item);
        Node node = new Node(item);
        node.value = item;
        if (size == 0) {
            first = node;
            last = node;
        }
        else {
            Node oldLast = last;    //Note here for further reference studies
            last = node;
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        removeValidator();
        Item item = first.value;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        removeValidator();
        Item item = last.value;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = first;
        public boolean hasNext() {
            return currentNode != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = currentNode.value;
            currentNode = currentNode.next;
            return item;
        }
    }

    private void addValidator(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add 'null'");
    }

    private void removeValidator() {
        if (size == 0) throw new NoSuchElementException("The Deque is empty");
    }

    public static void main(String[] args) {

    }
}
