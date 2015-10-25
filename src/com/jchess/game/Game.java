package com.jchess.game;

import com.jchess.board.Board;
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
     * Construct a new game.
     */
    public Game() {
        board = new DefaultInitializer().init();
        moves = new ArrayList<>();
        players = new HashMap<>();
    }

    /**
     * Add new player to the game and let the game pick an available color.
     * @param player player to add
     * @return the assigned color
     * @throws JCJoinGameException if player was not successfully added to the game
     */
    public Color addPlayer(Player player) throws JCJoinGameException {
        if (players.size() == 2)
            throw new JCJoinGameException("Game is full.");

        // Find available color, default to white
        Color color = Color.White;
        if (players.containsKey(Color.White))
            color = Color.Black;

        return addPlayer(color, player);
    }

    /**
     * Add new player to the game with the specified color.
     * @param color
     * @param player player to add
     * @return the assigned color
     * @throws JCJoinGameException if player was not successfully added to the game
     */
    public Color addPlayer(Color color, Player player) throws JCJoinGameException {
        if (players.containsKey(color))
            throw new JCJoinGameException("Color '" + color + "' already in the game.");

        if (players.containsValue(player))
            throw new JCJoinGameException("Player '" + player + "' already in the game.");

        players.put(color, player);

        return color;
    }
}
