package com.jchess.ui;

import com.jchess.game.Game;
import com.jchess.game.Player;
import com.jchess.ui.drawers.FontPieceDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class ChessGUI extends JFrame {
    private static final Logger LOG = Logger.getLogger(ChessGUI.class.getName());

    private Game game;

    private JPanel chessBoard;

    public ChessGUI(Game game) throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JChess Online");

        add(chessBoard);

        setSize(600, 600);
        setVisible(true);

        this.game = game;
    }
    private void createUIComponents() {
        chessBoard = new ChessBoardPanel(game.getBoard(), new FontPieceDrawer());
    }

    public static void main(String[] args) {
        Game game = new Game(new Player(0, "Player 1"), new Player(1, "Player 2"));
        new ChessGUI(game);
    }
}
