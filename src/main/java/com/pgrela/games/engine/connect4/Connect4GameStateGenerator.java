package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.GeneratorImpl;
import com.pgrela.games.engine.api.Move;

import java.util.HashMap;
import java.util.Map;

public class Connect4GameStateGenerator implements GeneratorImpl<Connect4Board> {

    @Override
    public Map<Move, Board> generate(Connect4Board gameState) {
        HashMap<Move, Board> nextBoards = new HashMap<>();
        for (int column = 0; column < Connect4Board.SIZE; column++) {
            if(!gameState.isColumnFull(column)){
                Connect4Move move = Connect4Move.getMove(column);
                nextBoards.put(move, apply(gameState, move));
            }
        }
        return nextBoards;
    }

    private Connect4Board apply(Connect4Board gameState, Connect4Move move) {
        Tile[][] newBoard = new Tile[Connect4Board.SIZE][Connect4Board.SIZE];
        for (int column = 0; column < Connect4Board.SIZE; column++) {
            for (int row = 0; row < Connect4Board.SIZE; row++) {
                newBoard[column][row] = gameState.board[column][row];
            }
        }
        int alteredColumn = move.getColumn();
        for (int row = 0; row < Connect4Board.SIZE; row++) {
            if (newBoard[alteredColumn][row].equals(Tile.BLANK)){
                newBoard[alteredColumn][row] = gameState.getNextPlayer().getSymbol();
                return new Connect4Board(newBoard, gameState.getNextPlayer().next());
            }
        }
        throw new IllegalStateException();
    }
}
