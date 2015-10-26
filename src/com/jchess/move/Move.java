package com.jchess.move;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Piece;

public class Move {
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
        board.move(src, dest);
        board.capture(capturedPiece);
    }

    public Square getSrc() {
        return src;
    }

    public Square getDest() {
        return dest;
    }
}
