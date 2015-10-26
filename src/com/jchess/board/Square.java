package com.jchess.board;

import com.jchess.game.Color;

public class Square {
    public final int row;
    public final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Return a cell with (-1,-1) if non was found so we can chain method calls.
     */
    public Square forward(Color color) {
        if (isNull())
            return new Square(-1, -1);

        Square square = new Square(row + color.direction, col);
        return (square.isValid()) ? square : new Square(-1, -1);
    }

    public Square backward(Color color) {
        if (isNull())
            return new Square(-1, -1);

        Square square = new Square(row - color.direction, col);
        return (square.isValid()) ? square : new Square(-1, -1);
    }

    public Square right(Color color) {
        if (isNull())
            return new Square(-1, -1);

        Square square = new Square(row, col + color.direction);
        return (square.isValid()) ? square : new Square(-1, -1);
    }

    public Square left(Color color) {
        if (isNull())
            return new Square(-1, -1);

        Square square = new Square(row, col - color.direction);
        return (square.isValid()) ? square : new Square(-1, -1);
    }

    public boolean isValid() {
        return (!isNull()) && (row > 0 && row <= Chessboard.ROWS) && (col > 0 && col <= Chessboard.COLUMNS);
    }

    public boolean isNull() {
        return (row == -1 && col == -1);
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
