package com.jchess.ui.drawers;

import com.jchess.board.Square;
import com.jchess.game.*;
import com.jchess.game.Color;
import com.jchess.ui.ChessboardPanel;
import com.jchess.util.crypto.Utility;

import java.awt.*;

public class FontPieceDrawer implements PieceDrawer {
    private final ChessboardPanel chessPanel;

    private Font font;

    public FontPieceDrawer(ChessboardPanel chessPanel, Font font) {
        this.chessPanel = chessPanel;
        this.font = font;
    }

    @Override
    public void draw(Graphics2D g, Piece piece, Square square) {
        Rectangle bounds = chessPanel.getBoardBounds();
        g.setFont(font);

        String letter = getLetter(piece);

        int x = bounds.x + (square.col - 1) * chessPanel.getSquareWidth();
        int y = bounds.y + (9 - square.row) * chessPanel.getSquareHeight();

        Utility.drawCenteredString(g, font, letter, chessPanel.getSquareWidth(), x, y);
    }

    private String getLetter(Piece piece) {
        switch (piece.getType()) {
            case PAWN:
                return (piece.getColor() == Color.WHITE) ? "p" : "o";

            case KNIGHT:
                return (piece.getColor() == Color.WHITE) ? "n" : "m";

            case BISHOP:
                return (piece.getColor() == Color.WHITE) ? "b" : "v";

            case ROOK:
                return (piece.getColor() == Color.WHITE) ? "r" : "t";

            case QUEEN:
                return (piece.getColor() == Color.WHITE) ? "q" : "w";

            case KING:
                return (piece.getColor() == Color.WHITE) ? "k" : "l";

            default:
                return "";
        }
    }
}
