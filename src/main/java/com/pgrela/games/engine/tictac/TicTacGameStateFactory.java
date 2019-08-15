package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.InitialStateFactory;

import static com.pgrela.games.engine.tictac.Symbol.*;

public class TicTacGameStateFactory implements InitialStateFactory {
    @Override
    public TicTacGameState initialState() {
        return new TicTacGameState(new Symbol[][]{{BLANK, BLANK, BLANK}, {BLANK, BLANK, BLANK}, {BLANK, BLANK, BLANK}}, TicTacPlayer.CIRCLER);
    }
    public TicTacGameState initialState2() {
        return new TicTacGameState(new Symbol[][]{{BLANK, CROSS, BLANK}, {CIRCLE, BLANK, BLANK}, {BLANK, BLANK, BLANK}}, TicTacPlayer.CIRCLER);
    }
}
