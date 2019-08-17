package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.api.Board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TicTacGameState implements Board {

    Symbol board[][];

    private TicTacPlayer lastPlayer;

    public static int c = 0;

    static final Map<Symbol, String> S = new HashMap<>();

    static {
        S.put(Symbol.BLANK, " ");
        S.put(Symbol.CROSS, "X");
        S.put(Symbol.CIRCLE, "O");
    }

    public TicTacGameState(Symbol[][] board, TicTacPlayer lastPlayer) {
        this.board = board;
        this.lastPlayer = lastPlayer;
        ++c;
    }

    @Override
    public TicTacPlayer getNextPlayer() {
        return lastPlayer.next();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicTacGameState)) return false;

        TicTacGameState that = (TicTacGameState) o;

        if (!Arrays.deepEquals(board, that.board)) return false;
        return lastPlayer.equals(that.lastPlayer);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + lastPlayer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(S.get(board[i][j]));
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
