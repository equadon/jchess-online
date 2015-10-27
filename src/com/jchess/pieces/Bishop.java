package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private boolean captureMove;

    public Bishop(Color color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square src) {
        List<Move> moves = new ArrayList<>();

        Square dest;

        // Forward-right
        captureMove = false;
        dest = src.forward(color).right(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.forward(color).right(color);
            if (captureMove)
                break;
        }

        // Forward-left
        captureMove = false;
        dest = src.forward(color).left(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.forward(color).left(color);
            if (captureMove)
                break;
        }

        // Backward-right
        captureMove = false;
        dest = src.backward(color).right(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.backward(color).right(color);
            if (captureMove)
                break;
        }

        // Backward-left
        captureMove = false;
        dest = src.backward(color).left(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.backward(color).left(color);
            if (captureMove)
                break;
        }

        return moves;
    }

    private boolean canMove(Chessboard board, Square square) {
        Piece piece = board.getPiece(square);

        if (piece != null && piece.getColor() != color)
            captureMove = true;

        return square.isValid() && (piece == null || piece.getColor() != color);
    }
}
