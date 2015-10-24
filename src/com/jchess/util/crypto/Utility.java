package com.jchess.util.crypto;

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
}