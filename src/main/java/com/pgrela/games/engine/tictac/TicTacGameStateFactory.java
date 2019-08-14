package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.GameState;
import com.pgrela.games.engine.InitialStateFactory;

import static com.pgrela.games.engine.tictac.Symbol.BLANK;
import static com.pgrela.games.engine.tictac.Symbol.CIRCLE;
import static com.pgrela.games.engine.tictac.Symbol.CROSS;

public class TicTacGameStateFactory implements InitialStateFactory {
    @Override
    public GameState initialState() {
        return new TicTacGameState(new Symbol[][]{{BLANK, BLANK, BLANK}, {BLANK, BLANK, BLANK}, {BLANK, BLANK, BLANK}}, TicTacPlayer.CIRCLER);
    }
    public GameState initialState2() {
        return new TicTacGameState(new Symbol[][]{{BLANK, CROSS, BLANK}, {CIRCLE, BLANK, BLANK}, {BLANK, BLANK, BLANK}}, TicTacPlayer.CIRCLER);
    }
}
