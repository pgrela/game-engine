package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;

import java.util.Arrays;

import static com.pgrela.games.engine.connect4.Connect4CompactBoardEvaluator.*;

public class Connect4CompactBoard implements Board {

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int LONGS = 4;
    public static final int CONNECT = 4;
    static final int ROW_LENGTH = 4;
    private static final int INITIAL_BLANKS = ROWS * COLUMNS;
    long[] board;
    private Connect4Player nextPlayer;
    private Integer hashCode = null;
    private int blanks;

    public Connect4CompactBoard(long[] board, Connect4Player nextPlayer) {
        this(board, nextPlayer, INITIAL_BLANKS);
    }

    public Connect4CompactBoard(long[] board, Connect4Player nextPlayer, int blanks) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.blanks = blanks;
    }

    @Override
    public Connect4Player getNextPlayer() {
        return nextPlayer;
    }


    boolean isBlank(int row, int column) {
        return getCode(row, column) == 0L;
    }

    long getCode(int row, int column) {
        if (column < ROW_LENGTH) {
            return getCodeFromLong(0, row, column);
        }
        return getCodeFromLong(3, row, column - 3);
    }

    static void setCode(long[] board, int row, int column, long symbol) {
        int startLong = Math.max(column - 3, 0);
        int endLong = Math.min(column, 3);
        for (int aLong = startLong; aLong <= endLong; aLong++) {
            setCodeForLong(board, aLong, row, column - aLong, symbol);
        }
    }

    private long getCodeFromLong(int aLong, int row, int column) {
        return (board[aLong] >> ((row * ROW_LENGTH + column)) * BITS) & MASK;
    }

    static void setCodeForLong(long[] board, int aLong, int row, int column, long symbol) {
        board[aLong] |= symbol << ((row * 4 + column) * BITS);
    }

    public boolean isColumnFull(int column) {
        return getCode(0, column) != BLANK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Tile[][] board = asArray();
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                stringBuilder.append(board[column][row].getSymbol());
            }
            stringBuilder.append("\n");
        }
        for (int i = 0; i < COLUMNS; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        Connect4CompactBoard that = (Connect4CompactBoard) o;

        if (blanks != that.blanks) {
            return false;
        }
        if (!Arrays.equals(board, that.board)) {
            return false;
        }
        return nextPlayer == that.nextPlayer;
    }

    @Override
    public int hashCode() {
        if (hashCode != null) {
            return hashCode;
        }
        int result = Arrays.hashCode(board);
        result = 31 * result + (nextPlayer != null ? nextPlayer.hashCode() : 0);
        result = 31 * result + blanks;
        return hashCode = result;
    }

    public int getBlanks() {
        return blanks;
    }

    Tile[][] asArray() {
        Tile[][] tiles = new Tile[COLUMNS][ROWS];
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                long code = getCode(row, column);
                tiles[column][row] = Arrays.stream(Tile.values()).filter(tile -> tile.getCode() == code)
                        .findFirst().get();
            }
        }
        return tiles;
    }
}
