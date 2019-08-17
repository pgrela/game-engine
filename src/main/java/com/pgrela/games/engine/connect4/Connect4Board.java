package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;

import java.util.Arrays;

public class Connect4Board implements Board {
    public static final int SIZE = 7;
    Tile[][] board = new Tile[SIZE][SIZE];
    private Connect4Player nextPlayer;
    public static int c=0;
    private Integer hashCode = null;

    public Connect4Board(Tile[][] board, Connect4Player nextPlayer) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        ++c;
    }

    @Override
    public Connect4Player getNextPlayer() {
        return nextPlayer;
    }

    public boolean isColumnFull(int column) {
        return board[column][6] != Tile.BLANK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                stringBuilder.append(board[j][i].symbol);
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

        Connect4Board that = (Connect4Board) o;

        if (!Arrays.deepEquals(board, that.board)) return false;
        return nextPlayer == that.nextPlayer;
    }

    @Override
    public int hashCode() {
        if(hashCode==null){
            hashCode = Arrays.deepHashCode(board);
            hashCode = 31 * hashCode + (nextPlayer != null ? nextPlayer.hashCode() : 0);
        }
        return hashCode;
    }
}
