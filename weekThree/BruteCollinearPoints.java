package com.zz.algorithm.weekThree;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        validate(points);
        this.points = points;
        int n = points.length;
        lineSegments = new LineSegment[n * (n - 1) / 2];
    }

    public           int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        for (int i = 0; i < points.length; i++) {
            if (i == 0) validate(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                if (i == 0) validate(points[j]);
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                try{
                    LineSegment lineSegment = new LineSegment(points[i], points[j]);
                    lineSegments[i] = lineSegment;
                }
                catch (NullPointerException e) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return lineSegments;
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
    }

    private void validate(Point point) {
        if (point == null) throw new IllegalArgumentException();
    }
}
