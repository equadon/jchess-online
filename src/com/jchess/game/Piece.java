package com.jchess.game;

import com.jchess.game.Color;
import com.jchess.game.Move;
import com.jchess.game.PieceType;

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
