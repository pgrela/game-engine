package com.pgrela.games.engine.tictac;

import com.google.common.collect.Lists;
import com.pgrela.games.engine.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TicTacGameState implements GameState {

    private Symbol board[][];

    private TicTacPlayer lastPlayer;

    public static int c=0;

    public TicTacGameState(Symbol[][] board, TicTacPlayer lastPlayer) {
        this.board = board;
        this.lastPlayer = lastPlayer;
        ++c;
    }

    @Override
    public Collection<TicTacGameState> next() {
        ArrayList<TicTacGameState> ticTacGameStates = Lists.newArrayList();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Symbol.BLANK) {
                    Symbol[][] newBoard = copyBoard();
                    newBoard[i][j] = getNextPlayer().getSymbol();
                    TicTacGameState ticTacGameState = new TicTacGameState(newBoard, getNextPlayer());
                    ticTacGameStates.add(ticTacGameState);
                }
            }
        }
        return ticTacGameStates;
    }

    private Symbol[][] copyBoard() {
        Symbol[][] symbols = new Symbol[3][3];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, symbols[i], 0, board[i].length);
        }
        return symbols;
    }

    @Override
    public TicTacEvaluation getEvaluation() {
        double circle = getEvaluation(Symbol.CIRCLE);
        double cross = getEvaluation(Symbol.CROSS);

        int blanks = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Symbol.BLANK) ++blanks;
            }
        }
        return new TicTacEvaluation(circle, cross, blanks);
    }

    private double getEvaluation(Symbol symbol) {
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
            int inLine = howMany(line, symbol);
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

    private int howMany(int[][] line, Symbol symbol) {
        int counter = 0;
        for (int[] point : line) {
            if (board[point[0]][point[1]] == symbol) {
                ++counter;
            }
        }
        return counter;
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
