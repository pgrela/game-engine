package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.GeneratorImpl;
import com.pgrela.games.engine.api.Move;

import java.util.HashMap;
import java.util.Map;

public class TicTacGameStateGenerator implements GeneratorImpl<TicTacGameState> {
    @Override
    public Map<Move, Board> generate(TicTacGameState gameState) {

        Map<Move, Board> ticTacGameStates = new HashMap<>();
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
