package com.zz.algorithm.week4;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int dimension;
    private int hamming = -1;
    private int manhattan = -1;
    private int indexOfBlank;
    private final int[] initialBoard;

    public Board(int[][] blocks) {
        dimension = blocks.length;
        initialBoard = new int[dimension * dimension];
        int index = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                initialBoard[index] = blocks[i][j];
                if (initialBoard[index] == 0) indexOfBlank = index;
                index++;
            }
        }
        calculateHM();
    }

    private Board(int[] blocks, int indexOfBlank, int hamming, int manhattan) {
        dimension = (int) Math.sqrt((double) blocks.length);
        initialBoard = blocks;
        this.indexOfBlank = indexOfBlank;
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        calculateHM();
        return hamming;
    }

    public int manhattan() {
        calculateHM();
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public Board twin() {
        int[] twinBoard = initialBoard.clone();
        if (indexOfBlank > 1) {
            int tmp = twinBoard[0];
            twinBoard[0] = twinBoard[1];
            twinBoard[1] = tmp;
        }
        else {
            int tmp = twinBoard[dimension];
            twinBoard[dimension] = twinBoard[dimension + 1];
            twinBoard[dimension + 1] = tmp;
        }
        Board twin = new Board(twinBoard, indexOfBlank, -1, -1);
        twin.calculateHM();
        return twin;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension != this.dimension || that.indexOfBlank != this.indexOfBlank) return false;
        int size = initialBoard.length;
        if (that.initialBoard[0] != this.initialBoard[0] || that.initialBoard[size - 1] != this.initialBoard[size - 1] || that.initialBoard[size / 2] != this.initialBoard[size / 2]) return false;
        for (int i = 1; i < initialBoard.length - 1; i++) {
            if (that.initialBoard[i] != this.initialBoard[i]) return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> list = new LinkedList<>();
        int row = indexOfBlank / dimension;
        int col = indexOfBlank % dimension;
        if (row > 0) {
            int[] newBoard = initialBoard.clone();
            newBoard[indexOfBlank] = newBoard[indexOfBlank - dimension];
            newBoard[indexOfBlank - dimension] = 0;
            list.add(new Board(newBoard, indexOfBlank - dimension, adjustedHamming(indexOfBlank - dimension), adjustedManhattan(indexOfBlank - dimension)));
        }
        if (row < dimension - 1) {
            int[] newBoard = initialBoard.clone();
            newBoard[indexOfBlank] = newBoard[indexOfBlank + dimension];
            newBoard[indexOfBlank + dimension] = 0;
            list.add(new Board(newBoard, indexOfBlank + dimension, adjustedHamming(indexOfBlank + dimension), adjustedManhattan(indexOfBlank + dimension)));
        }
        if (col > 0) {
            int[] newBoard = initialBoard.clone();
            newBoard[indexOfBlank] = newBoard[indexOfBlank - 1];
            newBoard[indexOfBlank - 1] = 0;
            list.add(new Board(newBoard, indexOfBlank - 1, adjustedHamming(indexOfBlank - 1), adjustedManhattan(indexOfBlank - 1)));
        }
        if (col < dimension - 1) {
            int[] newBoard = initialBoard.clone();
            newBoard[indexOfBlank] = newBoard[indexOfBlank + 1];
            newBoard[indexOfBlank + 1] = 0;
            list.add(new Board(newBoard, indexOfBlank + 1, adjustedHamming(indexOfBlank + 1), adjustedManhattan(indexOfBlank + 1)));
        }
        return list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension).append("\n");
        int j = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            sb.append(String.format("%2d ", initialBoard[i]));
            j++;
            if (j == dimension) {
                sb.append("\n");
                j = 0;
            }
        }
        return sb.toString();
    }

//    public int hashCode() {
//        return Arrays.hashCode(initialBoard);
//    }

    private void calculateHM() {
        if (hamming > -1 && manhattan > -1) return;
        hamming = 0;
        manhattan = 0;
        for (int i = 0; i < initialBoard.length; i++) {
            int value = initialBoard[i];
            if (value != i + 1 && value > 0) {
                hamming++;
                manhattan += Math.abs((value - 1) / dimension - i / dimension) + Math.abs((value - 1) % dimension - i % dimension);
            }
        }
    }

    private int adjustedHamming(int index) {
        return hamming + blockHamming(indexOfBlank, initialBoard[index]) - blockHamming(index, initialBoard[index]);
    }

    private int adjustedManhattan(int index) {
        return manhattan + blockManhattan(indexOfBlank, initialBoard[index]) - blockManhattan(index, initialBoard[index]);
    }

    private int blockHamming(int index, int value) {
        return value - 1 == index ? 0 : 1;
    }

    private int blockManhattan(int index, int value) {
        return Math.abs((value - 1) / dimension - index / dimension) + Math.abs((value - 1) % dimension - index % dimension);
    }

}