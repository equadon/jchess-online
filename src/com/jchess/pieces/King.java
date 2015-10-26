package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class King extends Piece {
    public King(Color color) {
        super(PieceType.KING, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square current) {
        throw new NotImplementedException();
    }
}
