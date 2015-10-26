package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(PieceType.PAWN, color);
    }

    @Override
    public Move[] validMoves() {
        return null;
    }
}
