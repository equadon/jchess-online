package com.jchess.ui;

import com.jchess.board.Square;
import com.jchess.game.Piece;
import com.jchess.ui.drawers.PieceDrawer;

import javax.swing.*;
import java.awt.*;

public class PiecePanel extends JPanel {
    private final ChessboardPanel chessboardPanel;
    private final Graphics2D g;
    private final PieceDrawer drawer;
    private final Piece piece;

    private int x;
    private int y;

    public PiecePanel(ChessboardPanel chessboardPanel, Graphics2D g, PieceDrawer drawer, Piece piece, Square square) {
        this.chessboardPanel = chessboardPanel;
        this.g = g;
        this.drawer = drawer;
        this.piece = piece;

        Rectangle bounds = chessboardPanel.getBoardBounds();

        x = bounds.x + (square.col - 1) * chessboardPanel.getSquareWidth();
        y = bounds.y + (9 - square.row) * chessboardPanel.getSquareHeight();

        drawer.draw(g, piece, x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
