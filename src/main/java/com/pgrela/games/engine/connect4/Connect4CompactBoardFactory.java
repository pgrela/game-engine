package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.InitialStateFactory;

public class Connect4CompactBoardFactory implements InitialStateFactory{
    @Override
    public Connect4CompactBoard initialState() {
        return new Connect4CompactBoard(new Connect4CompactBoard.MagicBoard(), Connect4Player.BLUE, 49);
    }
}
