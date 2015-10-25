package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.game.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Queen extends Piece {
    public Queen(Color color) {
        super(PieceType.QUEEN, color);
    }

    @Override
    public Move[] validMoves() {
        throw new NotImplementedException();
    }
}
