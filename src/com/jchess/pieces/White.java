package com.jchess.pieces;

public enum White implements PieceType {
    Pawn('p'),
    Knight('n'),
    Bishop('v'),
    Rook('r'),
    Queen('q'),
    King('k');

    private char letter;

    public char getLetter() {
        return letter;
    }

    White(char letter) {
        this.letter = letter;
    }
}
