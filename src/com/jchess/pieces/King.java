package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color) {
        super(PieceType.KING, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square src) {
        List<Move> moves = new ArrayList<>();

        Square[] allMoves = {
                src.forward(color),
                src.backward(color),
                src.left(color),
                src.right(color),
                src.forward(color).left(color),
                src.forward(color).right(color),
                src.backward(color).left(color),
                src.backward(color).right(color)
        };

        for (Square square : allMoves)
            if (canMove(board, square))
                moves.add(new Move(board, src, square));

        return moves;
    }

    private boolean canMove(Chessboard board, Square dest) {
        Piece piece = board.getPiece(dest);

        return dest.isValid() && (piece == null || piece.getColor() != color);
    }
}
