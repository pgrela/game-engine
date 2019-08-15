package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.GameStateGenerator;

import java.util.HashMap;
import java.util.Map;

public class TicTacGameStateGenerator implements GameStateGenerator<TicTacMove, TicTacGameState> {
    @Override
    public Map<TicTacMove, TicTacGameState> next(TicTacGameState gameState) {

        Map<TicTacMove, TicTacGameState> ticTacGameStates = new HashMap<>();
        for (int i = 0; i < gameState.board.length; i++) {
            for (int j = 0; j < gameState.board[i].length; j++) {
                if (gameState.board[i][j] == Symbol.BLANK) {
                    Symbol[][] newBoard = copyBoard(gameState.board);
                    Symbol symbol = gameState.getNextPlayer().getSymbol();
                    newBoard[i][j] = symbol;
                    TicTacGameState ticTacGameState = new TicTacGameState(newBoard, gameState.getNextPlayer());
                    ticTacGameStates.put(new TicTacMove(symbol, i, j), ticTacGameState);
                }
            }
        }
        return ticTacGameStates;
    }

    private Symbol[][] copyBoard(Symbol[][] board) {
        Symbol[][] symbols = new Symbol[3][3];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, symbols[i], 0, board[i].length);
        }
        return symbols;
    }
}
