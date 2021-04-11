import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class KdTree {

    private int size;
    private Node root;

    private static class Node {
        Node left;
        Node right;
        Point2D point;

        Node(Point2D point) {
            left = null;
            right = null;
            this.point = point;
        }
    }

    public KdTree() {
        size = 0;
        root = null;
    }                              // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                     // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    public void insert(Point2D p) {
        checkIfNull(p);
        if (recursivelyAdd(p, true))
            size++;
    }             // add the point to the set (if it is not already in the set)

    private boolean recursivelyAdd(Point2D p, boolean leftRight) {
        if (root == null) {
            root = new Node(p);
            return true;
        }

        Node curNode = root;
        while (true) {
            if (curNode.point.equals(p))
                return false;
            if (leftRight ? p.x() < curNode.point.x() : p.y() < curNode.point.y()) {
                if (curNode.left == null) {
                    curNode.left = new Node(p);
                    return true;
                } else
                    curNode = curNode.left;
            }
            else {
                if (curNode.right == null) {
                    curNode.right = new Node(p);
                    return true;
                } else
                    curNode = curNode.right;
            }
            leftRight = !leftRight;
        }
    }

    public boolean contains(Point2D p) {
        checkIfNull(p);
        return recursivelySearch(root, p, true);
    }           // does the set contain point p?

    private boolean recursivelySearch(Node root, Point2D p, boolean leftRight) {
        if (root == null)
            return false;
        else if (p.equals(root.point))
            return true;
        else if (leftRight ? p.x() < root.point.x() : p.y() < root.point.y()) {
            return recursivelySearch(root.left, p, !leftRight);
        }   else {
            return recursivelySearch(root.right, p, !leftRight);
        }
    }

    public void draw() {
        recursiveDraw(root);
    }

    private void recursiveDraw(Node root) {
        if (root == null)
            return;

        root.point.draw();
        recursiveDraw(root.left);
        recursiveDraw(root.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new LinkedList<>();
        if (root == null)
            return list;
        checkIfNull(rect);
        recursivelyFindRange(root, rect, list, true);
        return list;
    }            // all points that are inside the rectangle (or on the boundary)

    private void recursivelyFindRange(Node root, RectHV rect, List<Point2D> list, boolean leftRight) {
        if (root == null)
            return;

        if (leftRight ? root.point.x() < rect.xmin() : root.point.y() < rect.ymin())
            recursivelyFindRange(root.right, rect, list, !leftRight);
        else if (leftRight ? root.point.x() > rect.xmax() : root.point.y() > rect.ymax())
            recursivelyFindRange(root.left, rect, list, !leftRight);
        else {
            recursivelyFindRange(root.left, rect, list, !leftRight);
            recursivelyFindRange(root.right, rect, list, !leftRight);
            if (rect.contains(root.point))
                list.add(root.point);
        }
    }

    public Point2D nearest(Point2D p) {
        if (root == null)
            return null;
        checkIfNull(p);
        return recursivelyFindNearest(root, p, root.point, p.distanceSquaredTo(root.point), true, 0, 0 );
    }

    /**
     *
     * @param root current node
     * @param target the query point
     * @param current current nearest point
     * @param curDistance current closest distance
     * @param leftRight if this invocation compares x
     * @param minLeftRight current horizontal distance when searching on the farther side
     * @param minUpDown current vertical distance when searching on the farther side
     * @return the nearest point found
     */
    private Point2D recursivelyFindNearest(Node root, Point2D target, Point2D current,
                                           double curDistance, boolean leftRight, double minLeftRight, double minUpDown) {
        if (root == null)
            return current;

        if (root.point.distanceSquaredTo(target) < curDistance) {
           current = root.point;
           curDistance = root.point.distanceSquaredTo(target);
        }

        double distanceToLine = leftRight ? root.point.x() - target.x() : root.point.y() - target.y();

        // search on the "correct" direction
        current = recursivelyFindNearest(distanceToLine > 0 ? root.left : root.right,
                                         target, current, curDistance, !leftRight, minLeftRight, minUpDown);

        curDistance = current.distanceSquaredTo(target);

        // search on the "wrong" direction
        if (Math.pow(distanceToLine, 2) + (leftRight ? minUpDown : minLeftRight) < curDistance)
        current = recursivelyFindNearest(distanceToLine > 0 ? root.right : root.left,
                                         target, current, curDistance, !leftRight,
                                         leftRight ? Math.pow(distanceToLine, 2) : minLeftRight,
                                         leftRight ? minUpDown : Math.pow(distanceToLine, 2));

        return current;
    }

    private void checkIfNull(Object o) {
        if (o == null) throw new IllegalArgumentException();
    }


//    public static void main(String[] args) throws FileNotFoundException {
//        Scanner sc = new Scanner(new File("D:\\my_repositories\\Algorithm-assignment\\week5\\test.txt"));
//        KdTree kd = new KdTree();
//        while (sc.hasNext()) {
//            sc.next();
//            kd.insert(new Point2D(sc.nextDouble(), sc.nextDouble()));
//        }
//        System.out.println(kd.nearest(new Point2D(0.65625, 0.34375)));
//    }
}
