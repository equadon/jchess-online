package com.jchess.board;

public class Square {
    public final int row;
    public final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Square))
            return false;

        Square square = (Square) other;

        return this.row == square.row && this.col == square.col;
    }
}
