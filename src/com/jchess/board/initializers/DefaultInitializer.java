package com.jchess.board.initializers;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Color;
import com.jchess.pieces.*;

public class DefaultInitializer implements BoardInitializer {
    @Override
    public void init(Chessboard chessboard) {
        Color white = Color.WHITE;
        Color black = Color.BLACK;

        for (int col = 1; col <= Chessboard.COLUMNS; col++) {
            // Pawns
            chessboard.setPiece(new Pawn(white), new Square(2, col));
            chessboard.setPiece(new Pawn(black), new Square(7, col));

            // Rooks
            if (col == 1 || col == 8) {
                chessboard.setPiece(new Rook(white), new Square(1, col));
                chessboard.setPiece(new Rook(black), new Square(8, col));
            }

            // Knights
            if (col == 2 || col == 7) {
                chessboard.setPiece(new Knight(white), new Square(1, col));
                chessboard.setPiece(new Knight(black), new Square(8, col));
            }

            // Bishops
            if (col == 3 || col == 6) {
                chessboard.setPiece(new Bishop(white), new Square(1, col));
                chessboard.setPiece(new Bishop(black), new Square(8, col));
            }
        }

        // Queens
        chessboard.setPiece(new Queen(white), new Square(1, 4));
        chessboard.setPiece(new Queen(black), new Square(8, 4));

        // Kings
        chessboard.setPiece(new King(white), new Square(1, 5));
        chessboard.setPiece(new King(black), new Square(8, 5));
    }
}
