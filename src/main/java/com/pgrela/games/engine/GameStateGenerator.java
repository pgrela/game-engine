package com.pgrela.games.engine;

import java.util.Map;

public interface GameStateGenerator<MOVE extends Move, GAME_STATE extends GameState> {
    Map<MOVE, GAME_STATE> next(GAME_STATE gameState);
}
