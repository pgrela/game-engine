package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.GameStateEvaluator;

public class TicTacGameStateEvaluator implements GameStateEvaluator<TicTacGameState> {
    @Override
    public TicTacEvaluation evaluate(TicTacGameState gameState) {
        double circle = getEvaluation(Symbol.CIRCLE, gameState.board);
        double cross = getEvaluation(Symbol.CROSS, gameState.board);

        int blanks = 0;
        for (int i = 0; i < gameState.board.length; i++) {
            for (int j = 0; j < gameState.board[i].length; j++) {
                if (gameState.board[i][j] == Symbol.BLANK) ++blanks;
            }
        }
        return new TicTacEvaluation(circle, cross, blanks);
    }

    private double getEvaluation(Symbol symbol, Symbol[][] board) {
        int[][][] lines = new int[8][3][2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lines[i][j][0] = i;
                lines[i][j][1] = j;
                lines[i + 3][j][0] = j;
                lines[i + 3][j][1] = i;
            }
            lines[6][i][0] = i;
            lines[6][i][1] = i;

            lines[7][i][0] = i;
            lines[7][i][1] = 2 - i;
        }
        double score = 0;
        for (int[][] line : lines) {
            int inLine = howMany(line, symbol, board);
            if (inLine == 3) {
                return 100;
            }
            if (inLine == 2) {
                score += 5;
            }
            if (inLine == 1) {
                score += 2;
            }
        }
        return score;
    }

    private int howMany(int[][] line, Symbol symbol, Symbol[][] board) {
        int counter = 0;
        for (int[] point : line) {
            if (board[point[0]][point[1]] == symbol) {
                ++counter;
            }
        }
        return counter;
    }
}
