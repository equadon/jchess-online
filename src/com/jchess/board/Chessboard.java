package com.jchess.board;

import com.jchess.game.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Piece getPiece(Square square) {
        for (Map.Entry<Piece, Square> entry : pieces.entrySet())
            if (entry.getValue().equals(square))
                return entry.getKey();

        return null;
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

    public boolean move(Square src, Square dest) {
        if (!src.equals(dest)) {
            pieces.put(getPiece(src), dest);
            return true;
        }

        return false;
    }

    public void capture(Piece piece) {
        pieces.remove(piece);
    }
}
