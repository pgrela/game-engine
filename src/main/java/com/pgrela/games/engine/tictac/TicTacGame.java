package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.Engine;
import com.pgrela.games.engine.GameState;
import com.pgrela.games.engine.Move;

public class TicTacGame {
    public static void main(String[] args) {
        Engine<TicTacMove, TicTacGameState> engine = new Engine<>(new TicTacGameStateEvaluator(), new TicTacGameStateGenerator());
        TicTacGameState state = new TicTacGameStateFactory().initialState();
        engine.start(state);
    }
}
