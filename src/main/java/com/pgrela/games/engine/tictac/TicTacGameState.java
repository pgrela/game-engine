package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.GameState;

import java.util.Arrays;

public class TicTacGameState implements GameState {

    Symbol board[][];

    private TicTacPlayer lastPlayer;

    public static int c=0;

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
        return "TicTacGameState{" +
                "board=" + Arrays.toString(board[0]) + Arrays.toString(board[1]) + Arrays.toString(board[2]) +
                ", lastPlayer=" + lastPlayer +
                '}';
    }
}
