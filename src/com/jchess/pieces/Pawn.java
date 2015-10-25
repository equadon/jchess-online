package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.game.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(PieceType.PAWN, color);
    }

    @Override
    public Move[] validMoves() {
        throw new NotImplementedException();
    }
}
