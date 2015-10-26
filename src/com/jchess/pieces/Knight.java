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

        Square forwRight = src.forward(color).forward(color).right(color);
        Square forwLeft = src.forward(color).forward(color).left(color);

        Square backRight = src.backward(color).backward(color).right(color);
        Square backLeft = src.backward(color).backward(color).left(color);

        Square rightForw = src.right(color).right(color).forward(color);
        Square rightBack = src.right(color).right(color).backward(color);

        Square leftForw = src.left(color).left(color).forward(color);
        Square leftBack = src.left(color).left(color).backward(color);

        if (canMove(board, forwRight))
            moves.add(new Move(board, src, forwRight));
        if (forwLeft.isValid() && canMove(board, forwLeft))
            moves.add(new Move(board, src, forwLeft));

        if (canMove(board, backRight))
            moves.add(new Move(board, src, backRight));
        if (canMove(board, backLeft))
            moves.add(new Move(board, src, backLeft));

        if (canMove(board, rightForw))
            moves.add(new Move(board, src, rightForw));
        if (canMove(board, rightBack))
            moves.add(new Move(board, src, rightBack));

        if (canMove(board, leftForw))
            moves.add(new Move(board, src, leftForw));
        if (canMove(board, leftBack))
            moves.add(new Move(board, src, leftBack));

        return moves;
    }

    private boolean canMove(Chessboard board, Square dest) {
        Piece piece = board.getPiece(dest);

        return dest.isValid() && (piece == null || piece.getColor() != color);
    }
}
