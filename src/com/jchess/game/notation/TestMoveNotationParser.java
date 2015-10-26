package com.jchess.game.notation;

import com.jchess.board.Square;
import com.jchess.game.Game;
import com.jchess.game.PieceType;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMoveNotationParser implements MoveNotationParser {
    private Square source;
    private Square destination;

    public TestMoveNotationParser(Game game, String text) {
        String[] cols = {"a", "b", "c", "d", "e", "f", "g", "h"};
        List columns = Arrays.asList(cols);

        Pattern p = Pattern.compile("([a-z])([\\d]) ([a-z])([\\d])");
        Matcher m = p.matcher(text);

        if (m.matches()) {
            int srcRow = Integer.parseInt(m.group(2));
            int srcColumn = 1 + columns.indexOf(m.group(1));
            source = new Square(srcRow, srcColumn);

            int destRow = Integer.parseInt(m.group(4));
            int destColumn = 1 + columns.indexOf(m.group(3));
            destination = new Square(destRow, destColumn);
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
}
