package com.jchess.pieces;

import com.jchess.game.Color;
import com.jchess.game.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class King extends Piece {
    public King(Color color) {
        super(PieceType.KING, color);
    }

    @Override
    public Move[] validMoves() {
        throw new NotImplementedException();
    }
}
