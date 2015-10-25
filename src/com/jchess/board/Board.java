package com.jchess.board;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private final Square[] squares;

    public Board() {
        squares = new Square[ROWS * COLUMNS];
    }

    public Square getSquare(int row, int col) {
        int i = (row * 8) + col;

        return squares[i];
    }

    public static int getIndex(int row, int col) {
        return row * ROWS + col;
    }

    public static int getRow(int i) {
        return i / ROWS;
    }

    public static int getColumn(int i) {
        return i % ROWS;
    }
}
