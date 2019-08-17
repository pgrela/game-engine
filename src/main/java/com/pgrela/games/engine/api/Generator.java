package com.pgrela.games.engine.api;

import java.util.Map;

public interface Generator {
    Map<Move, Board> next(Board board);
}
