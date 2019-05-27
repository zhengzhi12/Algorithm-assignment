package com.zz.algorithm;

public class StopWatch {

    private final double start;
    private int count;
    private double last;

    public StopWatch() {
        start = System.nanoTime();
        count = 0;
        last = 0;
    }

    public double elapsedTime() {
        return (System.nanoTime() - start) / 1000000;
    }

    public void printTime() {
         double now = System.nanoTime();
         System.out.println("Display time " + count++ + " : " + (now - start) / 1000000);
         System.out.println("Elapsed since last call :" + (now - last) / 1000000);
         last = now;
    }
}
