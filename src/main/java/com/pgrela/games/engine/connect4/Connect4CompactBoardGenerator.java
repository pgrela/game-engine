package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.GeneratorImpl;
import com.pgrela.games.engine.api.Move;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.pgrela.games.engine.connect4.Connect4CompactBoard.*;
import static com.pgrela.games.engine.connect4.Connect4CompactBoardEvaluator.BITS;

public class Connect4CompactBoardGenerator implements GeneratorImpl<Connect4CompactBoard> {

    @Override
    public Map<Move, Board> generate(Connect4CompactBoard board) {
        HashMap<Move, Board> nextBoards = new HashMap<>();
        for (int column = 0; column < COLUMNS; column++) {
            if (!board.isColumnFull(column)) {
                Connect4Move move = Connect4Move.getMove(column);
                nextBoards.put(move, apply(board, move));
            }
        }
        return nextBoards;
    }

    Connect4CompactBoard apply(Connect4CompactBoard board, Connect4Move move) {
        MagicBoard magicBoard = new MagicBoard(board.board);
        int alteredColumn = move.getColumn();
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board.isBlank(row, alteredColumn)) {
                magicBoard.setCode(row, alteredColumn, board.getNextPlayer().getSymbol().getCode());
                return new Connect4CompactBoard(magicBoard, board.getNextPlayer().next(),
                        board.getBlanks() - 1);
            }
        }
        throw new IllegalStateException();
    }

}
