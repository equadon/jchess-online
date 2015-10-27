package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private boolean captureMove;

    public Rook(Color color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square src) {
        List<Move> moves = new ArrayList<>();

        Square dest;

        // Forward
        captureMove = false;
        dest = src.forward(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.forward(color);
            if (captureMove)
                break;
        }

        // Backward
        captureMove = false;
        dest = src.backward(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.backward(color);
            if (captureMove)
                break;
        }

        // Left
        captureMove = false;
        dest = src.left(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.left(color);
            if (captureMove)
                break;
        }

        // Right
        captureMove = false;
        dest = src.right(color);
        while (canMove(board, dest)) {
            moves.add(new Move(board, src, dest));
            dest = dest.right(color);
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
