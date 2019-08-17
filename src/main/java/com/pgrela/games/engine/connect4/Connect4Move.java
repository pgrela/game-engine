package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Move;

import java.util.HashMap;
import java.util.Map;

public class Connect4Move implements Move {
    private int column;
    private static Map<Integer, Connect4Move> MOVES;

    static {
        Map<Integer, Connect4Move> moves = new HashMap<>();
        for (int column = 0; column < 7; column++) {
            moves.put(column, new Connect4Move(column));
        }
        MOVES = moves;
    }

    private Connect4Move(int column) {
        this.column = column;
    }

    public static Connect4Move getMove(int column){
        return MOVES.get(column);
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Connect4Move{" +
                "column=" + column +
                '}';
    }
}
