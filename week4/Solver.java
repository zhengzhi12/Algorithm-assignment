package com.zz.algorithm.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;


import java.util.LinkedList;
import java.util.List;


public class Solver {

    private int moves;
    private final boolean solvable;
    private List<Board> solution;

    private class Node implements Comparable<Node> {
        private final int curMoves;
        private final int priority;
        private final Board board;
        private final Node prev;

        private Node(Board board, Node prev, int curMoves, int manhattan) {
            this.curMoves = curMoves;
            priority = curMoves + manhattan;
            this.board = board;
            this.prev = prev;
        }

        public int compareTo(Node that) {
            int cmp = Integer.compare(this.priority, that.priority);
            if (cmp == 0)
                return Integer.compare(this.priority - this.curMoves, that.priority - that.curMoves);
            else return cmp;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> pq = new MinPQ<>();
        Board twin = initial.twin();
        pq.insert(new Node(initial, null, 0, initial.manhattan()));
        pq.insert(new Node(twin, null, 0, twin.manhattan()));
//        Set<Board> set = new HashSet<>();
        while (!pq.min().board.isGoal()) {
            Node node = pq.delMin();
            Iterable<Board> it = node.board.neighbors();
            for (Board b : it) {
                if (!b.equals(node.prev == null ? null : node.prev.board)) {
//                    if (set.add(b))
                    pq.insert(new Node(b, node, node.curMoves + 1, b.manhattan()));
                }
            }
        }
        Node finalNode = pq.min();
        moves = finalNode.curMoves;
        solution = new LinkedList<>();
        while (true) {
            solution.add(0, finalNode.board);
            if (finalNode.prev == null) break;
            finalNode = finalNode.prev;
        }
        solvable = !(finalNode.board == twin);
        if (!solvable) {
            solution = null;
            moves = -1;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
