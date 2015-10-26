package com.jchess.pieces;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.move.Move;
import com.jchess.game.Piece;
import com.jchess.game.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(PieceType.PAWN, color);
    }

    @Override
    public List<Move> validMoves(Chessboard board, Square src) {
        List<Move> moves = new ArrayList<>();

        Square forward = src.forward(color);
        Square doubleForward = forward.forward(color);
        Square diagonalLeft = src.forwardDiagLeft(color);
        Square diagonalRight = src.forwardDiagRight(color);

        if (canMoveForward(board, forward))
            moves.add(new Move(board, src, forward));

        if (canMoveDoubleForward(board, src, doubleForward))
            moves.add(new Move(board, src, doubleForward));

        if (canMoveDiagonally(board, diagonalLeft))
            moves.add(new Move(board, src, diagonalLeft));

        if (canMoveDiagonally(board, diagonalRight))
            moves.add(new Move(board, src, diagonalRight));

        return moves;
    }

    private boolean canMoveForward(Chessboard board, Square dest) {
        return board.isSquareEmpty(dest);
    }

    private boolean canMoveDoubleForward(Chessboard board, Square src, Square dest) {
        return isAtStartRow(src) && canMoveForward(board, dest);
    }

    private boolean canMoveDiagonally(Chessboard board, Square dest) {
        Piece piece = board.getPiece(dest);

        return (piece != null && piece.getColor() != color);
    }

    private boolean isAtStartRow(Square src) {
        if (color == Color.WHITE)
            return src.row == 2;
        else
            return src.row == 7;
    }
}
