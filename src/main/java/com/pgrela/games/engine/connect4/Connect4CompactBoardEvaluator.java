package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.EvaluatorImpl;
import com.pgrela.games.engine.dummy.TwoPlayersBinaryEvaluation;
import com.pgrela.games.engine.dummy.TwoPlayersEvaluation;

import java.util.ArrayList;
import java.util.List;

public class Connect4CompactBoardEvaluator implements EvaluatorImpl<Connect4CompactBoard> {
    static private List<List<Long>> LINES = new ArrayList<>();
    static long CROSS = 1;
    static long CIRCLE = 2;
    static long BLANK = 0;
    static long MASK = 3;
    static long BITS = 2;
    private static long CROSSES = 0;
    private static long CIRCLES = 0;

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

    private static long line4(long... cords) {
        long r = 0;
        if (cords.length != 4) throw new IllegalArgumentException();
        for (int i = 0; i < 4; i++) {
            r |= MASK << (BITS * cords[i]);
        }
        return r;
    }


    static {
        for (int i = 0; i < 4; i++) {
            LINES.add(new ArrayList<>());
        }
        // row lines
        for (int aLong = 0; aLong < 4; aLong++) {
            for (int row = 0; row < 7; row++) {
                long[] line = new long[4];
                for (int column = 0; column < 4; column++) {
                    line[column] = row * 4 + column;
                }
                LINES.get(aLong).add(line4(line));
            }
        }
        // column lines                                
        for (int startColumn = 0; startColumn < 4; startColumn++) {
            for (int startRow = 0; startRow < 4; startRow++) {
                long[] line = new long[4];
                for (int point = 0; point < 4; point++) {
                    line[point] = startRow * (4 + point) + startColumn;
                }
                LINES.get(0).add(line4(line));
            }
        }
        for (int startColumn = 1; startColumn < 4; startColumn++) {
            for (int startRow = 0; startRow < 4; startRow++) {
                long[] line = new long[4];
                for (int point = 0; point < 4; point++) {
                    line[point] = startRow * (4 + point) + startColumn;
                }
                LINES.get(3).add(line4(line));
            }
        }
        // diagonals down-right lines
        for (int aLong = 0; aLong < 4; aLong++) {
            for (int startRow = 0; startRow < 4; startRow++) {
                long[] line = new long[4];
                for (int column = 0; column < 4; column++) {
                    line[column] = startRow * 4 + 5 * column;
                }
                LINES.get(aLong).add(line4(line));
            }
        }
        // diagonals down-left lines
        for (int aLong = 0; aLong < 4; aLong++) {
            for (int startRow = 0; startRow < 4; startRow++) {
                long[] line = new long[4];
                for (int column = 0; column < 4; column++) {
                    line[column] = ((startRow + column) * 4 + 3) - column;
                }
                LINES.get(aLong).add(line4(line));
            }
        }
    }

    @Override
    public Evaluation evaluateBoard(Connect4CompactBoard board) {
        for (int i = 0; i < 4; i++) {
            for (long line : LINES.get(i)) {
                long aLine = board.board[i] & line;
                if (aLine == (line & CROSSES)) {
                    return new TwoPlayersEvaluation(Connect4Player.RED);
                }
                if (aLine == (line & CIRCLES)) {
                    return new TwoPlayersEvaluation(Connect4Player.BLUE);
                }
            }
        }
        if (board.blanks == 0) {
            return TwoPlayersBinaryEvaluation.DRAWN;
        }
        return TwoPlayersBinaryEvaluation.DRAWING;
    }
}
