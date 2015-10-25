package com.jchess.game;

import com.jchess.board.Board;
import com.jchess.board.initializers.BoardInitializer;
import com.jchess.board.initializers.DefaultInitializer;
import com.jchess.exceptions.JCJoinGameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;

    private Map<Color, Player> players;
    private List<Move> moves;

    /**
     * Construct a new game with the default board initializer.
     */
    public Game(Player player1, Player player2) {
        this(player1, player2, new DefaultInitializer());
    }

    /**
     * Construct a new game with an initializer.
     */
    public Game(Player player1, Player player2, BoardInitializer initializer) {
        moves = new ArrayList<>();

        players = new HashMap<>();
        players.put(Color.White, player1);
        players.put(Color.Black, player2);

        board = initializer.init();
    }

    public Board getBoard() {
        return board;
    }
}
