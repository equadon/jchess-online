package com.jchess.game;

public enum Color {
    BLACK(-1),
    WHITE(1);

    private int direction;

    Color(int direction) {
        this.direction = direction;
    }

    public Color opponent() {
        return (direction == 1) ? BLACK : WHITE;
    }
}
