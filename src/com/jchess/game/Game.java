package com.jchess.game;

import com.jchess.board.Chessboard;
import com.jchess.board.initializers.BoardInitializer;
import com.jchess.board.initializers.DefaultInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Chessboard chessboard;

    private Map<Color, Player> players;
    private List<Move> moves;

    private Color currentPlayer;

    /**
     * Construct a new game with the default chessboard initializer.
     */
    public Game(Player player1, Player player2) {
        this(player1, player2, new DefaultInitializer());
    }

    /**
     * Construct a new game with an initializer.
     */
    public Game(Player player1, Player player2, BoardInitializer initializer) {
        chessboard = new Chessboard();
        moves = new ArrayList<>();

        players = new HashMap<>();
        players.put(Color.White, player1);
        players.put(Color.Black, player2);

        currentPlayer = Color.White;

        initializer.init(chessboard);
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
}
