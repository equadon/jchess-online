package com.jchess.board;

import com.jchess.game.Color;

public class Square {
    public final int row;
    public final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Square forward(Color color) {
        Square square = new Square(row + color.direction, col);

        return (square.isValid()) ? square : null;
    }

    public Square backward(Color color) {
        Square square = new Square(row - color.direction, col);

        return (square.isValid()) ? square : null;
    }

    public Square left(Color color) {
        Square square = new Square(row, col - color.direction);

        return (square.isValid()) ? square : null;
    }

    public Square right(Color color) {
        Square square = new Square(row, col + color.direction);

        return (square.isValid()) ? square : null;
    }

    public boolean isValid() {
        return (row > 0 && row <= Chessboard.ROWS) && (col > 0 && col <= Chessboard.COLUMNS);
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
