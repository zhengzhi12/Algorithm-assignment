import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;

public class PointSET {

    private final Set<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }                              // construct an empty set of points

    public boolean isEmpty() {
        return set.isEmpty();
    }                     // is the set empty?

    public int size() {
        return set.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        checkIfNull(p);
        set.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        checkIfNull(p);
        return set.contains(p);
    }           // does the set contain point p?

    public void draw() {
        Iterator<Point2D> iter = set.iterator();
        while (iter.hasNext()) {
            iter.next().draw();
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        checkIfNull(rect);
        List<Point2D> res = new LinkedList<>();

        Iterator<Point2D> iter = set.iterator();
        while (iter.hasNext()) {
            Point2D point = iter.next();
            if (rect.contains(point)) {
                res.add(point);
            }
        }

        return res;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        checkIfNull(p);
        Point2D nearestPoint = null;
        double distance = Integer.MAX_VALUE;

        Iterator<Point2D> iter = set.iterator();
        while (iter.hasNext()) {
            Point2D currentPoint = iter.next();
            double distanceCache = currentPoint.distanceSquaredTo(p);
            if (distanceCache < distance) {
                nearestPoint = currentPoint;
                distance = distanceCache;
            }
        }            // a nearest neighbor in the set to point p; null if the set is empty

        return nearestPoint;
    }

    private void checkIfNull(Object o) {
        if (o == null) throw new IllegalArgumentException();
    }
}