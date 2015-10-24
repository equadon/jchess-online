package com.jchess.board;

public class Board {
    public static final int ROWS = 8;
    public static final int COLS = 8;

    private final Square[] squares;

    public Board() {
        squares = new Square[ROWS * COLS];
    }

    public Square getSquare(int row, int col) {
        int i = (row * 8) + col;

        return squares[i];
    }
}
