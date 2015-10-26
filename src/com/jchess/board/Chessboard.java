package com.jchess.board;

import com.jchess.game.Piece;

import java.util.HashMap;
import java.util.Map;

public class Chessboard {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private final Map<Piece, Square> pieces;

    public Chessboard() {
        pieces = new HashMap<>();
    }

    public Map<Piece, Square> getPieces() {
        return pieces;
    }

    public void setPiece(Piece piece, Square square) {
        pieces.put(piece, square);
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
