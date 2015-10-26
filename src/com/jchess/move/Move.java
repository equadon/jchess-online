package com.jchess.move;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Piece;

import java.util.List;
import java.util.logging.Logger;

public class Move {
    private static final Logger LOG = Logger.getLogger(Move.class.getName());

    private final Chessboard board;
    private final Square src;
    private final Square dest;
    private final Piece capturedPiece;

    public Move(Chessboard board, Square src, Square dest) {
        this.board = board;
        this.src = src;
        this.dest = dest;
        this.capturedPiece = board.getPiece(dest);
    }

    public void execute() {
        if (!src.equals(dest)) {
            Piece srcPiece = board.getPiece(src);

            List<Move> validMoves = srcPiece.validMoves(board, src);

            if (validMoves.contains(this)) {
                if (board.move(src, dest)) {
                    board.capture(capturedPiece);
                    if (capturedPiece == null)
                        LOG.info("Move: (" + src.row + "," + src.col + ") -> (" + dest.row + "," + dest.col + ")");
                    else
                        LOG.info("Move: (" + src.row + "," + src.col + ") -> (" + dest.row + "," + dest.col + ") and captured a " + capturedPiece.getType());
                } else {
                }
            }
        }
    }

    public Square getSrc() {
        return src;
    }

    public Square getDest() {
        return dest;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Move))
            return false;

        Move move = (Move) other;

        return this.src.equals(move.getSrc()) && this.dest.equals(move.getDest());
    }
}
