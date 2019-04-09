package com.zz.algorithm.weekTwo;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (count < k) {
                rq.enqueue(s);
            }
            /**
             * This is Reservoir Sampling, to make every element survive at a k/n probability without knowing n,
             * just make each "i"th element survive at an i/k probability, and each survived element has a 1/k probability
             * to be replaced.
             * This can be achieved by simply replace array[random] with the element ready to enter if random is smaller than k.
             * Think about why when k = 10 and i starts from 11 to n
             */
            else {
                int random = StdRandom.uniform(count + 1);
                if (random < k) {
                    rq.dequeue();
                    rq.enqueue(s);
                }
            }
            count++;
        }
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
