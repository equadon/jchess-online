package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square src) {
        List<Move> moves = new ArrayList<>();

        Square[] destSquares = {
                src.forward(color).forward(color).right(color),
                src.forward(color).forward(color).left(color),

                src.backward(color).backward(color).right(color),
                src.backward(color).backward(color).left(color),

                src.right(color).right(color).forward(color),
                src.right(color).right(color).backward(color),

                src.left(color).left(color).forward(color),
                src.left(color).left(color).backward(color)
        };

        for (Square dest : destSquares)
            if (canMove(board, dest))
                moves.add(new Move(board, src, dest));

        return moves;
    }

    private boolean canMove(Chessboard board, Square dest) {
        Piece piece = board.getPiece(dest);

        return dest.isValid() && (piece == null || piece.getColor() != color);
    }
}
