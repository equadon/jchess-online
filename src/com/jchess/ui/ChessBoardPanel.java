package com.jchess.ui;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.game.Game;
import com.jchess.game.Piece;
import com.jchess.ui.drawers.FontPieceDrawer;
import com.jchess.ui.drawers.PieceDrawer;
import com.jchess.util.crypto.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.logging.Logger;

public class ChessboardPanel extends JPanel implements MouseListener {
    private static final Logger LOG = Logger.getLogger(ChessboardPanel.class.getName());

    public static final int BORDER = 30; // pixels

    public static final Color TEXT_COLOR = new Color(60, 60, 60);
    public static final Color BG_COLOR = new Color(231, 175, 111);
    public static final Color SQUARE_COLOR = new Color(137, 89, 51);

    private Font chessFont;
    private Font squareFont;

    private Game game;

    private PieceDrawer pieceDrawer;

    private Square srcClick;

    public ChessboardPanel(Game game) {
        this.game = game;

        squareFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

        addMouseListener(this);
    }

    public Rectangle getBoardBounds() {
        Rectangle bounds = getBounds();

        return new Rectangle(bounds.x + BORDER, bounds.y + BORDER, bounds.width - (2 * BORDER), bounds.height - (2 * BORDER));
    }

    public int getSquareWidth() {
        return getBoardBounds().width / Chessboard.COLUMNS;
    }

    public int getSquareHeight() {
        return getBoardBounds().height / Chessboard.ROWS;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        chessFont = new Font("Chess Cases", Font.PLAIN, (int) (0.9 * getSquareWidth()));
        pieceDrawer = new FontPieceDrawer(this, chessFont);

        // Draw chessboard
        drawBoard(g2d);

        // Draw letters and numbers
        drawCellNumbers(g2d);

        drawPieces(g2d);
    }

    public int getRow(int y) {
        int relY = y - BORDER;

        return 8 - relY / getSquareHeight();
    }

    public int getColumn(int x) {
        int relX = x - BORDER;

        return 1 + relX / getSquareWidth();
    }

    private void drawPieces(Graphics2D g) {
        for (Map.Entry<Piece, Square> entry : game.getPieces().entrySet())
            pieceDrawer.draw(g, entry.getKey(), entry.getValue());
    }

    private void drawBoard(Graphics2D g) {
        Rectangle bounds = getBoardBounds();
        int width = getSquareWidth();
        int height = getSquareHeight();

        // Background color
        g.setColor(BG_COLOR);
        g.fillRect(bounds.x, bounds.y, width * Chessboard.COLUMNS, height * Chessboard.ROWS);

        // Squares
        g.setColor(SQUARE_COLOR);
        for (int row = 0; row < Chessboard.ROWS; row++) {
            int col = (row % 2 == 0) ? 1 : 0;

            for (; col < Chessboard.COLUMNS; col += 2)
                g.fillRect(bounds.x + (col * width), bounds.y + (row * height), width, height);
        }

        // Border
        g.drawRect(bounds.x, bounds.y, width * Chessboard.COLUMNS, height * Chessboard.ROWS);
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
        for (int col = 0; col < Chessboard.COLUMNS; col++) {
            int strWidth = metrics.stringWidth(letters[col]);

            int x = bounds.x + col * width;

            int topY = bounds.y - (int) (metrics.getHeight() / 2.0) + 5;
            int bottomY = (int) bounds.getMaxY() + (int) (metrics.getHeight() / 2.0);

            Utility.drawCenteredString(g, squareFont, letters[col], getSquareWidth(), x, topY);
            Utility.drawCenteredString(g, squareFont, letters[col], getSquareWidth(), x, bottomY);
        }

        // Numbers (columns)
        for (int row = 0; row < Chessboard.ROWS; row++) {
            int strWidth = metrics.stringWidth(numbers[row]);
            int y = bounds.y + row * height + (int) (getSquareHeight() / 2.0) + (int) (metrics.getHeight() / 3.0);

            g.drawString(numbers[row], bounds.x - strWidth - 3, y);
            g.drawString(numbers[row], (int) bounds.getMaxX(), y);
        }
    }

    private boolean isInsideBoard(MouseEvent e) {
        Rectangle bounds = getBoardBounds();

        return (e.getX() >= bounds.x && e.getX() <= bounds.getMaxX()) &&
                (e.getY() >= bounds.y && e.getY() <= bounds.getMaxY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1 && isInsideBoard(e)) {
            int row = getRow(e.getY());
            int col = getColumn(e.getX());

            srcClick = new Square(row, col);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1 && srcClick != null && isInsideBoard(e)) {
            int row = getRow(e.getY());
            int col = getColumn(e.getX());

            Square dest = new Square(row, col);
            game.tmpMove(srcClick, dest);
            repaint();

            srcClick = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
