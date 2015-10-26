package com.jchess.game.notation;

import com.jchess.board.Square;

public interface MoveNotationParser {
    Square getSrc();
    Square getDest();
}
