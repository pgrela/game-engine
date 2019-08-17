package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.InitialStateFactory;

import static com.pgrela.games.engine.connect4.Connect4Board.SIZE;

public class Connect4GameStateFactory implements InitialStateFactory{
    @Override
    public Connect4Board initialState() {
        Tile[][] board = new Tile[SIZE][SIZE];
        for (int column = 0; column < SIZE; column++) {
            for (int row = 0; row < SIZE; row++) {
                board[column][row] = Tile.BLANK;
            }
        }
        return new Connect4Board(board, Connect4Player.BLUE);
    }
}
