package com.jchess.board;

import com.jchess.pieces.Piece;

public class Square {
    private Piece piece;
    private int row;
    private int col;

    public Piece getPiece() {
        return piece;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
