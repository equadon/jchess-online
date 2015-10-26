package com.jchess.game.notation;

import com.jchess.board.Square;
import com.jchess.game.Game;
import com.jchess.game.PieceType;
import com.jchess.game.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgebraicMoveNotationParser implements MoveNotationParser {
    private Square source;
    private Square destination;

    public AlgebraicMoveNotationParser(Game game, String text) {
        String[] cols = {"a", "b", "c", "d", "e", "f", "g", "h"};
        List columns = Arrays.asList(cols);

        // TODO: Support notation other than normal moves
        Pattern p = Pattern.compile("([A-Z]*)([a-z]+)([\\d]+)");
        Matcher m = p.matcher(text);

        if (m.matches()) {
            PieceType piece = parsePiece(m.group(1));
            int column = 1 + columns.indexOf(m.group(2));
            int row = Integer.parseInt(m.group(3));
        }
    }

    @Override
    public Square getSrc() {
        return source;
    }

    @Override
    public Square getDest() {
        return destination;
    }

    private PieceType parsePiece(String piece) {
        switch (piece) {
            case "K":
                return PieceType.KING;
            case "Q":
                return PieceType.QUEEN;
            case "R":
                return PieceType.ROOK;
            case "B":
                return PieceType.BISHOP;
            case "N":
                return PieceType.KNIGHT;
            default:
                return PieceType.PAWN;
        }
    }
}
