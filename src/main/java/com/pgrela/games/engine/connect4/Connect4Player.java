package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Player;

import java.util.HashMap;
import java.util.Map;

public enum Connect4Player implements Player {
    RED, BLUE;

    private static Map<Connect4Player, Tile> TO_TILE = new HashMap<>();
    private static Map<Tile, Connect4Player> TO_PLAYER = new HashMap<>();
    static {
        connect(Tile.BLUE, BLUE);
        connect(Tile.RED, RED);
    }
    private static void connect(Tile tile, Connect4Player player){
        TO_PLAYER.put(tile, player);
        TO_TILE.put(player, tile);
    }

    public Tile getSymbol() {
        return TO_TILE.get(this);
    }

    public Connect4Player next() {
        return this.equals(RED) ? BLUE : RED;
    }


    public static Player ownerOf(Tile tile) {
        return TO_PLAYER.get(tile);
    }
}
