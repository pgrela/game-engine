package com.pgrela.games.engine.connect4.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pgrela.games.engine.connect4.Connect4CompactBoard.COLUMNS;
import static com.pgrela.games.engine.connect4.Connect4CompactBoard.CONNECT;
import static com.pgrela.games.engine.connect4.Connect4CompactBoard.ROWS;
import static com.pgrela.games.engine.connect4.game.MagicBoard.BITS;
import static com.pgrela.games.engine.connect4.game.MagicBoard.BLANK;
import static com.pgrela.games.engine.connect4.game.MagicBoard.CIRCLE;
import static com.pgrela.games.engine.connect4.game.MagicBoard.CROSS;
import static com.pgrela.games.engine.connect4.game.MagicBoard.MASK;

public class MagicBoardEvaluator {
    static private List<Long> LEFT_LINES = new ArrayList<>();
    static private List<Long> RIGHT_LINES = new ArrayList<>();
    private static long CROSSES = 0;
    private static long CIRCLES = 0;

    static class Coords {
        int row, column;

        public Coords(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    /**
     * 1  0,1,2,3,
     * 2  4,5,6,7,
     * 3  8,
     * 4  12,
     * 5  16,
     * 6  20,
     * 7  24,
     */
    static {
        for (int i = 0; i < 64 / BITS; i++) {
            CIRCLES |= CIRCLE << (i * BITS);
            CROSSES |= CROSS << (i * BITS);
        }
    }

    private static void line4(Coords... cords) {
        if (cords.length != 4) throw new IllegalArgumentException();
        MagicBoard magicBoard = new MagicBoard();
        for (int i = 0; i < 4; i++) {
            magicBoard.setCode(cords[i].getRow(), cords[i].getColumn(), MASK);
        }
        if (Arrays.stream(cords).mapToInt(Coords::getColumn).max().getAsInt() < 5) {
            LEFT_LINES.add(magicBoard.left);
        } else {
            RIGHT_LINES.add(magicBoard.right);
        }
    }


    static {
        for (int startRow = 0; startRow < ROWS; startRow++) {
            for (int startColumn = 0; startColumn < COLUMNS; startColumn++) {
                if (startColumn < CONNECT) {
                    Coords[] coords = new Coords[4];
                    for (int i = 0; i < CONNECT; i++) {
                        coords[i] = new Coords(startRow, startColumn + i);
                    }
                    line4(coords);
                }
                if (startRow < CONNECT) {
                    Coords[] coords = new Coords[4];
                    for (int i = 0; i < CONNECT; i++) {
                        coords[i] = new Coords(startRow + i, startColumn);
                    }
                    line4(coords);
                }
                if (startRow < CONNECT && startColumn < CONNECT) {
                    Coords[] coords = new Coords[4];
                    for (int i = 0; i < CONNECT; i++) {
                        coords[i] = new Coords(startRow + i, startColumn + i);
                    }
                    line4(coords);
                }
                if (startRow < CONNECT && startColumn < CONNECT) {
                    Coords[] coords = new Coords[4];
                    for (int i = 0; i < CONNECT; i++) {
                        coords[i] = new Coords(startRow + i, startColumn + 3 - i);
                    }
                    line4(coords);
                }
            }
        }
    }

    public static long evaluateBoard(MagicBoard board) {
        for (long line : LEFT_LINES) {
            if ((board.left & line) == (line & CROSSES)) {
                return CROSS;
            }
            if ((board.left & line) == (line & CIRCLES)) {
                return CIRCLE;
            }
        }
        for (long line : RIGHT_LINES) {
            if ((board.right & line) == (line & CROSSES)) {
                return CROSS;
            }
            if ((board.right & line) == (line & CIRCLES)) {
                return CIRCLE;
            }
        }
        return BLANK;
    }
}
