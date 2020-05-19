package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.InitialStateFactory;
import com.pgrela.games.engine.connect4.game.MagicBoard;

public class Connect4CompactBoardFactory implements InitialStateFactory{
    @Override
    public Connect4CompactBoard initialState() {
        return new Connect4CompactBoard(new MagicBoard(), Connect4Player.BLUE, 49);
    }
}
