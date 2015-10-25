package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.game.Move;

public abstract class Piece {
    private PieceType type;
    private Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public abstract Move[] validMoves();
}
