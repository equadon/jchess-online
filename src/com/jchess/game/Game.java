package com.jchess.game;

import com.jchess.board.Chessboard;
import com.jchess.board.Square;
import com.jchess.board.initializers.BoardInitializer;
import com.jchess.board.initializers.NewBoardInitializer;
import com.jchess.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Chessboard chessboard;

    private Map<Color, Player> players;
    private List<Move> moves;

    private Color currentColor;

    /**
     * Construct a new game with the new game initializer.
     */
    public Game(Player player1, Player player2) {
        this(player1, player2, new NewBoardInitializer());
    }

    /**
     * Construct a new game with an initializer.
     */
    public Game(Player player1, Player player2, BoardInitializer initializer) {
        chessboard = new Chessboard();
        moves = new ArrayList<>();

        players = new HashMap<>();
        players.put(Color.WHITE, player1);
        players.put(Color.BLACK, player2);

        currentColor = Color.WHITE;

        initializer.init(chessboard);
    }

    public Map<Piece, Square> getPieces() {
        return chessboard.getPieces();
    }

    public Piece getPiece(Square square) {
        return chessboard.getPiece(square);
    }

    public Move[] getValidMoves(Square square) {
        Piece piece = chessboard.getPiece(square);

        if (piece != null) {
            List<Move> moves = piece.validMoves(chessboard, square);
            if (moves.size() > 0)
                return moves.toArray(new Move[moves.size()]);
        }

        return null;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void execute(Move move) {
        move.execute();
    }

    /**
     * Temporary until proper move code is implemented?
     */
    public void tmpMove(Square src, Square dest) {
        execute(new Move(chessboard, src, dest));
    }
}
