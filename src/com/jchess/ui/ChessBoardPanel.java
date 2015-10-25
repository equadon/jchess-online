package com.jchess.ui;

import com.jchess.board.Board;
import com.jchess.game.Piece;
import com.jchess.ui.drawers.PieceDrawer;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    public static final int BORDER = 30; // pixels

    public static final Color TEXT_COLOR = new Color(60, 60, 60);
    public static final Color BG_COLOR = new Color(231, 175, 111);
    public static final Color SQUARE_COLOR = new Color(137, 89, 51);

    private static final Font chessFont = new Font("Chess Cases", Font.PLAIN, 64);
    private static final Font squareFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    private Board board;

    private PieceDrawer pieceDrawer;

    public ChessBoardPanel(Board board, PieceDrawer pieceDrawer) {
        this.board = board;
        this.pieceDrawer = pieceDrawer;
    }

    public Rectangle getBoardBounds() {
        Rectangle bounds = getBounds();

        return new Rectangle(bounds.x + BORDER, bounds.y + BORDER, bounds.width - (2 * BORDER), bounds.height - (2 * BORDER));
    }

    public int getSquareWidth() {
        return (int) (getBoardBounds().width / Board.COLUMNS);
    }

    public int getSquareHeight() {
        return (int) (getBoardBounds().height / Board.ROWS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw board
        drawBoard(g2d);

        // Draw letters and numbers
        drawCellNumbers(g2d);

        drawPieces(g2d);
    }

    private void drawPieces(Graphics2D g) {
    }

    private void drawPiece(Graphics2D g, Piece piece) {
        pieceDrawer.draw(g, piece);
    }

    private void drawBoard(Graphics2D g) {
        Rectangle bounds = getBoardBounds();
        int width = getSquareWidth();
        int height = getSquareHeight();

        // Background color
        g.setColor(BG_COLOR);
        g.fillRect(bounds.x, bounds.y, width * Board.COLUMNS, height * Board.ROWS);

        // Squares
        g.setColor(SQUARE_COLOR);
        for (int row = 0; row < Board.ROWS; row++) {
            int col = (row % 2 == 0) ? 1 : 0;

            for (; col < Board.COLUMNS; col += 2)
                g.fillRect(bounds.x + (col * width), bounds.y + (row * height), width, height);
        }

        // Border
        g.drawRect(bounds.x, bounds.y, width * Board.COLUMNS, height * Board.ROWS);
    }

    private void drawCellNumbers(Graphics2D g) {
        Rectangle bounds = getBoardBounds();

        int width = getSquareWidth();
        int height = getSquareHeight();

        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] numbers = {"8", "7", "6", "5", "4", "3", "2", "1"};

        // Square letters and numbers
        g.setFont(squareFont);
        g.setColor(TEXT_COLOR);

        FontMetrics metrics = g.getFontMetrics(squareFont);

        // Letters (rows)
        for (int col = 0; col < Board.COLUMNS; col++) {
            int strWidth = metrics.stringWidth(letters[col]);
            int x = bounds.x + col * width + (int) (width / 2.0) - (int) (strWidth / 2.0);

            g.drawString(letters[col], x, bounds.y - (int) (metrics.getHeight() / 2.0) + 5);
            g.drawString(letters[col], x, (int) bounds.getMaxY() + (int) (metrics.getHeight() / 2.0));
        }

        // Numbers (columns)
        for (int row = 0; row < Board.ROWS; row++) {
            int strWidth = metrics.stringWidth(numbers[row]);
            int y = bounds.y + row * height + (int) (getSquareHeight() / 2.0) + (int) (metrics.getHeight() / 3.0);

            g.drawString(numbers[row], bounds.x - strWidth - 3, y);
            g.drawString(numbers[row], (int) bounds.getMaxX(), y);
        }
    }
}
