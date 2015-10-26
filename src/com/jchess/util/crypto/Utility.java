package com.jchess.util.crypto;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {
    private static BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

    public static String readUserInput(String prompt) {
        System.out.print(prompt);

        try {
            return userInput.readLine();
        } catch (IOException ex) {
            return "";
        }
    }

    public static void drawCenteredString(Graphics2D g, Font font, String text, int width, int x, int y) {
        int strWidth = (int) g.getFontMetrics(font).getStringBounds(text, g).getWidth();
        int xOffset = width/2 - strWidth/2;

        g.drawString(text, xOffset + x, y);
    }
}