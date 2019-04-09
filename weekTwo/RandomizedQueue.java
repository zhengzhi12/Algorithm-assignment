package com.zz.algorithm.weekTwo;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[4]; //initialization with 4 can reduce resize operations at small size
        size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        validator(item);
        if (size >= array.length) resize(array.length * 2);
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The Queue is empty");
        int randomIndex = StdRandom.uniform(size);
        size--;
        Item item = array[randomIndex]; //fetch the item with random index
        array[randomIndex] = array[size];   //in case of leaving a hole in the middle, move the last Item to this spot
        array[size] = null; //make the last Item null
        if (size <= array.length / 4) resize(array.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The Queue is empty");
        return array[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<Item> {
        private Item[] items;
        private int itemSize;

        private BagIterator() {
            items = (Item[]) new Object[size];
            itemSize = items.length;
            System.arraycopy(array, 0, items, 0, itemSize);
        }

        public boolean hasNext() {
            return itemSize != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * This method is basically copied from {@code dequeue()} method, as opposed to shuffle the array using Random.shuffle()
         * @return Item
         */
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randomIndex = StdRandom.uniform(itemSize);
            itemSize--;
            Item item = items[randomIndex];
            items[randomIndex] = items[itemSize];
            items[itemSize] = null;
            return item;
        }
    }

    private void resize(int capacity) {
        if (capacity < 4) return;   //ensure the array is no less than 4
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    private void validator(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add 'null'");
    }

    public static void main(String[] args) {
    }
}

