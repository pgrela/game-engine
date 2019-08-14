package com.pgrela.games.engine;

import com.pgrela.games.engine.tictac.TicTacGameState;

import java.util.Collection;

public interface GameState {
    Collection<TicTacGameState> next();

    GameStateEvaluation getEvaluation();

    Player getNextPlayer();
}
