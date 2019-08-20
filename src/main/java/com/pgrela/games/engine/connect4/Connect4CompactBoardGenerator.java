package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.GeneratorImpl;
import com.pgrela.games.engine.api.Move;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Connect4CompactBoardGenerator implements GeneratorImpl<Connect4CompactBoard> {

    @Override
    public Map<Move, Board> generate(Connect4CompactBoard board) {
        HashMap<Move, Board> nextBoards = new HashMap<>();
        for (int column = 0; column < Connect4Board.SIZE; column++) {
            if(!board.isColumnFull(column)){
                Connect4Move move = Connect4Move.getMove(column);
                nextBoards.put(move, apply(board, move));
            }
        }
        return nextBoards;
    }

    private Connect4Board apply(Connect4CompactBoard board, Connect4Move move) {
        long[] newBoard = Arrays.copyOf(board.board,4);
        for (int column = 0; column < Connect4Board.SIZE; column++) {
            for (int row = 0; row < Connect4Board.SIZE; row++) {
                newBoard[column][row] = board.board[column][row];
            }
        }
        int alteredColumn = move.getColumn();
        for (int row = 0; row < Connect4Board.SIZE; row++) {
            if (newBoard[alteredColumn][row].equals(Tile.BLANK)){
                newBoard[alteredColumn][row] = board.getNextPlayer().getSymbol();
                return new Connect4Board(newBoard, board.getNextPlayer().next());
            }
        }
        throw new IllegalStateException();
    }
}
