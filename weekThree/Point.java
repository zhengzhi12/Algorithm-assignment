package com.zz.algorithm.weekThree;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y > that.y) return  1;
        if (this.y < that.y) return -1;
        else return that.x - this.x == 0 ? 0 : (this.x - that.x) / Math.abs(that.x - this.x);
    }

    public double slopeTo(Point that) {
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (that.x == this.x) return Double.POSITIVE_INFINITY;
        if (that.y == this.y) return 0;
        // need to cast to double, mark this
        else return (that.y - this.y) / (double) (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 > slope2) return 1;
            if (slope1 < slope2) return -1;
            else return 0;
        }
    }

    public static void main(String[] args) {
        Point point = new Point(0, 0);
        Comparator<Point> comparator = point.slopeOrder();
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        System.out.println("p1 slope = " + point.slopeTo(p1));
        System.out.println("p2 slope = " + point.slopeTo(p2));
        System.out.println(comparator.compare(p1, p2));
    }
}