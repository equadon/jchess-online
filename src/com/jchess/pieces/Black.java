package com.jchess.pieces;

public enum Black implements PieceType {
    Pawn('o'),
    Knight('m'),
    Bishop('b'),
    Rook('t'),
    Queen('w'),
    King('l');

    private char letter;

    public char getLetter() {
        return letter;
    }

    Black(char letter) {
        this.letter = letter;
    }
}
