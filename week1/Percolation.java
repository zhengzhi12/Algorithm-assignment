import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] open;
    private final int length;
    private int openedSitesCount;
    private boolean percolates;
    private final WeightedQuickUnionUF bottomUF;
    private final WeightedQuickUnionUF topUF;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        length = n;
        openedSitesCount = 0;
        percolates = false;
        bottomUF = new WeightedQuickUnionUF(n * n + 2);
        topUF = new WeightedQuickUnionUF(n * n + 2);
        open = new boolean[n * n + 2];
        for (int i = 1; i < n + 1; i++) {
            topUF.union(0, i);
            bottomUF.union(n * n + 1, n * n + 1 - i);
        }
    }

    private int getArrayIndex(int row, int col) {
        if (row < 1 || row > length || col < 1 || col > length)
            throw new IllegalArgumentException("column or row input must be between 1 and n");
        return (row - 1) * length + col;
    }

    public void open(int row, int col) {
        int index = getArrayIndex(row, col);
        if (isOpen(row, col))
            return;

        unionNeighbor(row + 1, col, index);
        unionNeighbor(row - 1, col, index);
        unionNeighbor(row, col + 1, index);
        unionNeighbor(row, col - 1, index);
        percolates = percolates || topUF.find(index) == topUF.find(0) && bottomUF.find(index) == bottomUF.find(length * length + 1);
        open[index] = true;
        openedSitesCount++;
    }

    private void unionNeighbor(int row, int col, int index) {
        if (row < 1 || row > length || col < 1 || col > length)
            return;
        if (isOpen(row, col)) {
            bottomUF.union(getArrayIndex(row, col), index);
            topUF.union(getArrayIndex(row, col), index);
        }
    }


    public boolean isOpen(int row, int col) {
        return open[getArrayIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && topUF.find(getArrayIndex(row, col)) == topUF.find(0);
    }

    public int numberOfOpenSites() {
        return openedSitesCount;
    }

    public boolean percolates() {
        return percolates;
    }
}