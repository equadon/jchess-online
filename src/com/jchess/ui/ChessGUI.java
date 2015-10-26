package com.jchess.ui;

import com.jchess.board.Square;
import com.jchess.game.Game;
import com.jchess.move.Move;
import com.jchess.game.Player;
import com.jchess.game.notation.MoveNotationParser;
import com.jchess.game.notation.TestMoveNotationParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class ChessGUI extends JFrame {
    private static final Logger LOG = Logger.getLogger(ChessGUI.class.getName());

    private Game game;

    private JPanel mainPanel;
    private JPanel infoPanel;
    private JLabel moveLabel;
    private JTextField moveText;
    private JPanel chessPanel;

    public ChessGUI(Game game) throws HeadlessException {
        this.game = game;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JChess Online");

        add(mainPanel);

        setSize(600, 600);
        setVisible(true);

        // Action performed
        moveText.addActionListener(e -> {
            String text = moveText.getText().trim();

            MoveNotationParser parser = new TestMoveNotationParser(game, text);

            Square src = parser.getSrc();
            Square dest = parser.getDest();

            if (src == null || dest == null) {
                JOptionPane.showMessageDialog(this, "Couldn't parse move: " + text);
            } else {
                game.tmpMove(src, dest);
                repaint();
                moveText.setText("");
            }
        });
    }

    private void createUIComponents() {
        chessPanel = new ChessboardPanel(game);
    }

    public static void main(String[] args) {
        Game game = new Game(new Player(0, "Player 1"), new Player(1, "Player 2"));
        new ChessGUI(game);
    }
}
