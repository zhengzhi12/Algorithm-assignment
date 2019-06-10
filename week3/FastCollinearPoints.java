package com.zz.algorithm.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;
    private ArrayList<LineSegment> lineList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        validate(points);
        this.points = points.clone();
        for (int i = 0; i < points.length; i++) {
            int[] index;
            index = new int[points.length - 1];
            int indexPointer = 0;
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                index[indexPointer++] = j;
            }
            quick3waySort(index, 0, index.length - 1, i);
        }
    }

    public           int numberOfSegments() {
        return lineList.size();
    }

    public LineSegment[] segments() {
        if (lineSegments == null) {
            lineSegments = new LineSegment[lineList.size()];
            lineList.toArray(lineSegments);
        }
        return lineSegments.clone();
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (i == 0) validate(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                if (i == 0) validate(points[j]);
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    private void validate(Point point) {
        if (point == null) throw new IllegalArgumentException();
    }


    private void quick3waySort(int[] index, int lo, int hi, int basePoint) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int value = index[lo];
        Point p = points[basePoint];
        int i = lo + 1;
        while (i <= gt) {
            double valueSlope = p.slopeTo(points[value]);
            double thisSlope = p.slopeTo(points[index[i]]);
            if (valueSlope > thisSlope) exch(index, i++, lt++);
            else if (valueSlope < thisSlope) exch(index, i, gt--);
            else i++;
        }
        if (gt - lt >= 2) {
            int[] sameSlopeIndex = new int[gt - lt + 2];
            sameSlopeIndex[0] = basePoint;
            System.arraycopy(index, lt, sameSlopeIndex, 1, sameSlopeIndex.length - 1);
            // this is the point why I write the quick3way sort myself
            addLineSegment(sameSlopeIndex);
        }
        quick3waySort(index, lo, lt - 1, basePoint);
        quick3waySort(index, gt + 1, hi, basePoint);
    }

    private void exch(int[] index, int i, int j) {
        int swap =  index[j];
        index[j] = index[i];
        index[i] = swap;
    }

    private void addLineSegment(int[] sameSlopeIndex) {
        int min = 0;
        int max = 0;
        for (int i = 0; i < sameSlopeIndex.length; i++) {
            if (points[sameSlopeIndex[i]].compareTo(points[sameSlopeIndex[min]]) < 0) min = i;
            if (points[sameSlopeIndex[i]].compareTo(points[sameSlopeIndex[max]]) > 0) max = i;
        }
        if (min == 0) lineList.add(new LineSegment(points[sameSlopeIndex[min]], points[sameSlopeIndex[max]]));
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println("Time cost: " + stopwatch.elapsedTime());
    }
}
