package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.GeneratorImpl;
import com.pgrela.games.engine.api.Move;

import java.util.HashMap;
import java.util.Map;

public class DummyGameStateGenerator implements GeneratorImpl<DummyState> {

    public Map<Move, Board> generate(DummyState gameState) {
        Map<Move, Board> generated = new HashMap<>(20);
        for (int i = 0; i < 10; i++) {
            generated.put(new DummyMove(i), new DummyState(gameState.getNumber() + i));
        }
        return generated;
    }
}
