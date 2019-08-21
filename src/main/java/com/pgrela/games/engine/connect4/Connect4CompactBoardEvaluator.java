package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.EvaluatorImpl;
import com.pgrela.games.engine.dummy.TwoPlayersBinaryEvaluation;
import com.pgrela.games.engine.dummy.TwoPlayersEvaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pgrela.games.engine.connect4.Connect4CompactBoard.*;

public class Connect4CompactBoardEvaluator implements EvaluatorImpl<Connect4CompactBoard> {
    static private List<Long> LEFT_LINES = new ArrayList<>();
    static private List<Long> RIGHT_LINES = new ArrayList<>();
    static long CROSS = 1;
    static long CIRCLE = 2;
    static long BLANK = 0;
    static long MASK = 3;
    static long BITS = 2;
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
        if (Arrays.stream(cords).mapToInt(Coords::getColumn).max().getAsInt()<5) {
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

    @Override
    public Evaluation evaluateBoard(Connect4CompactBoard board) {
        for (long line : LEFT_LINES) {
            if((board.board.left & line) == (line&CROSSES)){
                return new TwoPlayersEvaluation(Connect4Player.RED);
            }
            if((board.board.left & line) == (line&CIRCLES)){
                return new TwoPlayersEvaluation(Connect4Player.BLUE);
            }
        }
        for (long line : RIGHT_LINES) {
            if((board.board.right & line) == (line&CROSSES)){
                return new TwoPlayersEvaluation(Connect4Player.RED);
            }
            if((board.board.right & line) == (line&CIRCLES)){
                return new TwoPlayersEvaluation(Connect4Player.BLUE);
            }
        }
        if (board.getBlanks() == 0) {
            return TwoPlayersBinaryEvaluation.DRAWN;
        }
        return TwoPlayersBinaryEvaluation.DRAWING;
    }
}
