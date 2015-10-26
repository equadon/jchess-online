package com.jchess.game;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.move.Move;

import java.util.List;

public abstract class Piece {
    protected PieceType type;
    protected Color color;

    protected boolean moved;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
        this.moved = false;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void move() {
        moved = true;
    }

    public abstract List<Move> validMoves(Chessboard board, Square current);
}
