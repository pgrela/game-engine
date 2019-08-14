package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.Engine;
import com.pgrela.games.engine.GameState;

public class TicTacGame {
    public static void main(String[] args) {
        Engine engine = new Engine();
        GameState state = new TicTacGameStateFactory().initialState();
        engine.start(state);
    }
}
