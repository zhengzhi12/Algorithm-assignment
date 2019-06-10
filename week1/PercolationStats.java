package com.zz.algorithm.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {
    private int trial;
    private double[] ratio;
    private double mean;
    private double stddev;

    private final static double CONSTANT_95 = 1.96;

    public PercolationStats(int n, int trials) {     // perform trials independent experiments on an n-by-n grid
        validate(n);
        validate(trials);
        this.trial = trials;
        ratio = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1,StdRandom.uniform(n) + 1);
            }
//            ratio[i] = percolation.numberOfOpenSites() / Math.pow(n, 2.0);
        }
    }
    public double mean() {                         // sample mean of percolation threshold
        mean =  StdStats.mean(ratio);
        return mean;
    }
    public double stddev() {                         // sample standard deviation of percolation threshold
        if (stddev == 1) return Double.NaN;
        stddev = StdStats.stddev(ratio);
        return stddev;
    }
    public double confidenceLo() {                   // low  endpoint of 95% confidence interval
        return mean - CONSTANT_95 * stddev / (Math.sqrt(trial));
    }
    public double confidenceHi() {                   // high endpoint of 95% confidence interval
        return mean + CONSTANT_95 * stddev / (Math.sqrt(trial));
    }

    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The grid size and trials must be greater than or equal to 1");
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats p = new PercolationStats(20, 100);
        System.out.println("mean = " + p.mean());
        System.out.println("standard deviation = " + p.stddev());
        System.out.println("The 95% confidence interval is " + p.confidenceLo() + "--" + p.confidenceHi());
        System.out.println("Time cost: " + stopwatch.elapsedTime());
    }
}
