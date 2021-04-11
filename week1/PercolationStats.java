import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {
    private final int trial;
    private final double[] ratio;
    private double mean;
    private double stddev;

    private final static double CONSTANT_95 = 1.96;

    public PercolationStats(int n, int trials) {
        validate(n);
        validate(trials);
        mean = Double.NaN;
        stddev = Double.NaN;
        this.trial = trials;
        ratio = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1,StdRandom.uniform(n) + 1);
            }
            ratio[i] = percolation.numberOfOpenSites() / Math.pow(n, 2.0);
        }
    }

    public double mean() {
        if (!Double.isNaN(mean))
            return mean;
        mean =  StdStats.mean(ratio);
        return mean;
    }

    public double stddev() {
        if (!Double.isNaN(stddev))
            return stddev;
        stddev = StdStats.stddev(ratio);
        return stddev;
    }

    public double confidenceLo() {                   // low  endpoint of 95% confidence interval
        return mean() - CONSTANT_95 * stddev() / (Math.sqrt(trial));
    }
    public double confidenceHi() {                   // high endpoint of 95% confidence interval
        return mean() + CONSTANT_95 * stddev() / (Math.sqrt(trial));
    }

    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The grid size and trials must be greater than or equal to 1");
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + p.mean());
        System.out.println("stddev = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
//        System.out.println("Time cost: " + stopwatch.elapsedTime());
    }
}
