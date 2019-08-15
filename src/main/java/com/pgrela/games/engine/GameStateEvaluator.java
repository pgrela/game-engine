package com.pgrela.games.engine;

public interface GameStateEvaluator<GAME_STATE extends GameState> {
    Evaluation evaluate(GAME_STATE gameState);
}
