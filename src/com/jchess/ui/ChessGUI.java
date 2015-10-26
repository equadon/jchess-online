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

    private JPanel chessPanel;

    public ChessGUI(Game game) throws HeadlessException {
        this.game = game;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JChess Online");

        add(chessPanel);

        setSize(600, 600);
        setVisible(true);
    }

    private void createUIComponents() {
        chessPanel = new ChessboardPanel(game.getChessboard());
    }

    public static void main(String[] args) {
        Game game = new Game(new Player(0, "Player 1"), new Player(1, "Player 2"));
        new ChessGUI(game);
    }
}
