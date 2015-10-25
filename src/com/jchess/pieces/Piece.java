package com.jchess.pieces;

import com.jchess.game.Move;

public abstract class Piece {
    private PieceType type;
    //public abstract Move[] validMoves();

    public Piece() {
        this(null);
    }

    public Piece(PieceType type) {
    }
}
