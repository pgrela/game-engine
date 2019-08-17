package com.pgrela.games.engine.api;

import java.util.Map;

public interface GeneratorImpl<GAME_STATE extends Board> extends Generator {
    Map<Move, Board> generate(GAME_STATE gameState);

    @Override
    default Map<Move, Board> next(Board board) {
        return generate((GAME_STATE) board);
    }
}
