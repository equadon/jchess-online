package com.jchess.game;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.move.Move;

import java.util.List;

public abstract class Piece {
    protected PieceType type;
    protected Color color;

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

    public abstract List<Move> validMoves(Chessboard board, Square current);
}
