package com.jchess.game;

public enum Color {
    BLACK(-1),
    WHITE(1);

    public final int direction;

    Color(int direction) {
        this.direction = direction;
    }

    public Color get(int direction) {
        return (direction == 1) ? WHITE : BLACK;
    }

    public Color opponent() {
        return (direction == 1) ? WHITE : BLACK;
    }
}
