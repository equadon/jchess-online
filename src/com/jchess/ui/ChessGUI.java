package com.jchess.ui;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class ChessGUI extends JFrame {
    private static final Logger LOG = Logger.getLogger(ChessGUI.class.getName());

    private JPanel chessBoard;

    public ChessGUI() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JChess Online");

        add(chessBoard);

        setSize(600, 600);
        setVisible(true);
    }
    private void createUIComponents() {
        chessBoard = new ChessBoardPanel();
    }

    public static void main(String[] args) {
        new ChessGUI();
    }
}
