package com.jchess.board.initializers;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.pieces.*;

public class NewBoardInitializer implements BoardInitializer {
    private Chessboard chessboard;

    @Override
    public void init(Chessboard chessboard) {
        this.chessboard = chessboard;

        Color white = Color.WHITE;
        Color black = Color.BLACK;

        // Pawns
        for (int col = 1; col <= Chessboard.COLUMNS; col++) {
            chessboard.setPiece(new Pawn(white), new Square(2, col));
            chessboard.setPiece(new Pawn(black), new Square(7, col));
        }

        // Rooks
        chessboard.setPiece(new Rook(white), new Square(1, 1));
        chessboard.setPiece(new Rook(white), new Square(1, 8));
        chessboard.setPiece(new Rook(black), new Square(8, 1));
        chessboard.setPiece(new Rook(black), new Square(8, 8));

        // Knights
        chessboard.setPiece(new Knight(white), new Square(1, 2));
        chessboard.setPiece(new Knight(white), new Square(1, 7));
        chessboard.setPiece(new Knight(black), new Square(8, 2));
        chessboard.setPiece(new Knight(black), new Square(8, 7));

        // Bishops
        chessboard.setPiece(new Bishop(white), new Square(1, 3));
        chessboard.setPiece(new Bishop(white), new Square(1, 6));
        chessboard.setPiece(new Bishop(black), new Square(8, 3));
        chessboard.setPiece(new Bishop(black), new Square(8, 6));

        // Queens
        chessboard.setPiece(new Queen(white), new Square(1, 4));
        chessboard.setPiece(new Queen(black), new Square(8, 4));

        // Kings
        chessboard.setPiece(new King(white), new Square(1, 5));
        chessboard.setPiece(new King(black), new Square(8, 5));
    }
}
