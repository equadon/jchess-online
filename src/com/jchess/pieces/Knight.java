package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.game.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Knight extends Piece {
    public Knight(Color color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public Move[] validMoves() {
        throw new NotImplementedException();
    }
}
