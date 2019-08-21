package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;

import java.util.Arrays;
import java.util.Objects;

import static com.pgrela.games.engine.connect4.Connect4CompactBoardEvaluator.*;

public class Connect4CompactBoard implements Board {

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int LONGS = 4;
    public static final int CONNECT = 4;
    static final int ROW_LENGTH = 5;
    private static final int INITIAL_BLANKS = ROWS * COLUMNS;
    final MagicBoard board;
    private final Connect4Player nextPlayer;
    private Integer hashCode = null;
    private final int blanks;

    static class MagicBoard {
        public static final int COLUMNS = 5;
        public static final int ROWS = 6;
        /**
         * 0,6,12,18,24
         * 1
         * 2
         * 3
         * 4
         * 5         29
         */
        long left;
        long right;

        public MagicBoard(MagicBoard board) {
            left=board.left;
            right=board.right;
        }

        public MagicBoard() {

        }

        long getCode(int row, int column) {
            if (column < COLUMNS) {
                return getCodeFromLong(left, row, column);
            }
            return getCodeFromLong(right, row, COLUMNS - 1 - (column - 2));
        }

        void setCode(int row, int column, long symbol) {
            if (column < COLUMNS) {
                left = setCodeForLong(left, row, column, symbol);
            }
            if (column > 1) {
                right = setCodeForLong(right, row, COLUMNS - 1 - (column - 2), symbol);
            }
        }

        private long getCodeFromLong(long aLong, int row, int column) {
            return (aLong >> ((column * ROWS + row)) * BITS) & MASK;
        }

        private long setCodeForLong(long aLong, int row, int column, long symbol) {
            return aLong | (symbol << (((column * ROWS + row)) * BITS));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MagicBoard that = (MagicBoard) o;
            return left == that.left &&
                    right == that.right;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left| right);
        }
    }

    public Connect4CompactBoard(MagicBoard board, Connect4Player nextPlayer) {
        this(board, nextPlayer, INITIAL_BLANKS);
    }

    public Connect4CompactBoard(MagicBoard board, Connect4Player nextPlayer, int blanks) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.blanks = blanks;
    }

    @Override
    public Connect4Player getNextPlayer() {
        return nextPlayer;
    }


    boolean isBlank(int row, int column) {
        return board.getCode(row, column) == 0L;
    }

    public boolean isColumnFull(int column) {
        return board.getCode(0, column) != BLANK;
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
        if ((board.left != that.board.left || board.right != that.board.right) && (board.left != that.board.right || board.right != that.board.left)) {
            return false;
        }
        return nextPlayer == that.nextPlayer;
    }

    @Override
    public int hashCode() {
        if (hashCode != null) {
            return hashCode;
        }
        int result = board.hashCode();
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
                long code = board.getCode(row, column);
                tiles[column][row] = Arrays.stream(Tile.values()).filter(tile -> tile.getCode() == code)
                        .findFirst().get();
            }
        }
        return tiles;
    }
}
