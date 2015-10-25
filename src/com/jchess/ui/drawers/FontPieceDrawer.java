package com.jchess.ui.drawers;

import com.jchess.game.Piece;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class FontPieceDrawer implements PieceDrawer {
    @Override
    public void draw(Graphics2D g, Piece piece) {
/*
        White pawn         p        P
        Black pawn         o        O
        White knight       n        N
        Black knight       m        M
        White bishop       b        B
        Black bishop       v        V
        White rook         r        R
        Black rook         t        T
        White queen        q        Q
        Black queen        w        W
        White king         k        K
        Black king         l        L

        Rectangle bounds = getBoardBounds();
        g.setFont(chessFont);

        String letter = "k";

        int x = bounds.x + col * getSquareWidth();
        int y = bounds.y + (row + 1) * getSquareHeight();

        g.drawString(letter, x, y);
*/
        throw new NotImplementedException();
    }
}
