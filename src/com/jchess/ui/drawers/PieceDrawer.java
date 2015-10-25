package com.jchess.ui.drawers;

import com.jchess.pieces.Piece;

import java.awt.*;

public interface PieceDrawer {
    void draw(Graphics2D g, Piece piece);
}
