package com.jchess.ui;

import javax.swing.*;
import java.util.logging.Logger;

public class ChessGUI extends JFrame {
    private static final Logger LOG = Logger.getLogger(ChessGUI.class.getName());
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame frame = new ChessGUI();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("JChess Online");
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
