package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;

import java.util.Arrays;

public class Connect4CompactBoard implements Board {
    public static final int SIZE = 7;
    long[] board = new long[4];
    private Connect4Player nextPlayer;
    public static int c = 0;
    private Integer hashCode = null;
    int blanks = 49;

    public Connect4CompactBoard(long[] board, Connect4Player nextPlayer, int previousBlanks) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.blanks = previousBlanks - 1;
        ++c;
    }

    @Override
    public Connect4Player getNextPlayer() {
        return nextPlayer;
    }

    public boolean isColumnFull(int column) {
        if (column < 4) {
            return (board[0] & (Connect4CompactBoardEvaluator.MASK << (Connect4CompactBoardEvaluator.BITS * column))) != 0L;
        }
        return (board[3] & (Connect4CompactBoardEvaluator.MASK << (Connect4CompactBoardEvaluator.BITS * (column - 3)))) != 0L;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                stringBuilder.append(board[i]);
            }
            stringBuilder.append("\n");
        }
        for (int i = 0; i < 7; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connect4CompactBoard that = (Connect4CompactBoard) o;

        if (blanks != that.blanks) return false;
        if (!Arrays.equals(board, that.board)) return false;
        return nextPlayer == that.nextPlayer;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(board);
        result = 31 * result + (nextPlayer != null ? nextPlayer.hashCode() : 0);
        result = 31 * result + blanks;
        return result;
    }
}
