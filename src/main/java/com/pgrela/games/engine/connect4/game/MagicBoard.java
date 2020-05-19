package com.pgrela.games.engine.connect4.game;

import java.util.Objects;

public class MagicBoard {
    public static final long CROSS = 1;
    public static final long CIRCLE = 2;
    public static final long BLANK = 0;
    public static final long MASK = 3;
    public static final long BITS = 2;
    private static final int COLUMNS = 5;
    private static final int ROWS = 6;
    /**
     * 0,6,12,18,24
     * 1
     * 2
     * 3
     * 4
     * 5         29
     */
    public long left;
    public long right;

    public MagicBoard(MagicBoard board) {
        left = board.left;
        right = board.right;
    }

    public MagicBoard() {

    }

    public long getCode(int row, int column) {
        if (column < COLUMNS) {
            return getCodeFromLong(left, row, column);
        }
        return getCodeFromLong(right, row, COLUMNS - 1 - (column - 2));
    }

    public void setCode(int row, int column, long symbol) {
        if (column < COLUMNS) {
            left = setCodeForLong(left, row, column, symbol);
        }
        if (column > 1) {
            right = setCodeForLong(right, row, COLUMNS - 1 - (column - 2), symbol);
        }
    }

    public void resetCode(int row, int column, long symbol) {
        if (column < COLUMNS) {
            left = resetCodeForLong(left, row, column, symbol);
        }
        if (column > 1) {
            right = resetCodeForLong(right, row, COLUMNS - 1 - (column - 2), symbol);
        }
    }

    private long resetCodeForLong(long aLong, int row, int column, long symbol) {
        long offset = ((column * ROWS + row)) * BITS;
        return (aLong ^ (~(MASK << offset))) | (symbol << offset);
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
        return Objects.hash(left | right);
    }
}
