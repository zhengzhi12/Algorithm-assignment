package com.zz.algorithm.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;
    private int lineIndex;

    public BruteCollinearPoints(Point[] points) {
        validate(points);
        this.points = points.clone();
        lineSegments = new LineSegment[4];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int l = j + 1; l < points.length; l++) {
                    for (int m = l + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]) && points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                            int[] sameSlopeIndex = {i, j, l, m};
                            addLineSegment(sameSlopeIndex);
                        }
                    }
                }
            }
        }
        LineSegment[] finalSeg = new LineSegment[lineIndex];
        System.arraycopy(lineSegments, 0, finalSeg, 0, lineIndex);
        lineSegments = finalSeg;
    }

    public           int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
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


    private void addLineSegment(int[] sameSlopeIndex) {
        int min = 0;
        int max = 0;
        for (int i = 0; i < sameSlopeIndex.length; i++) {
            if (points[sameSlopeIndex[i]].compareTo(points[sameSlopeIndex[min]]) < 0) min = i;
            if (points[sameSlopeIndex[i]].compareTo(points[sameSlopeIndex[max]]) > 0) max = i;
        }
        if (lineIndex == lineSegments.length) {
            LineSegment[] newLineSeg = new LineSegment[lineSegments.length * 2];
            System.arraycopy(lineSegments, 0, newLineSeg, 0, lineSegments.length);
            lineSegments = newLineSeg;
        }
        lineSegments[lineIndex++] = new LineSegment(points[sameSlopeIndex[min]], points[sameSlopeIndex[max]]);
    }

    public static void main(String[] args) {
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
