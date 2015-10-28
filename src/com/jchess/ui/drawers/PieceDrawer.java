package com.jchess.ui.drawers;

import com.jchess.board.Square;
import com.jchess.game.Piece;

import java.awt.*;

public interface PieceDrawer {
    void draw(Graphics2D g, Piece piece, Square square);
    void draw(Graphics2D g, Piece piece, int x, int y);
}
