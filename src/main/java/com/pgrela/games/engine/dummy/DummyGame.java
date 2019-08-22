package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.*;

import java.util.HashMap;
import java.util.Map;

public class DummyGame implements EvaluatorImpl<DummyState>, GeneratorImpl<DummyState>,  InitialStateFactory {
    @Override
    public DummyState initialState() {
        return new DummyState(0);
    }

    public Map<Move, Board> generate(DummyState gameState) {
        Map<Move, Board> generated = new HashMap<>(20);
        for (int i = 0; i < 10; i++) {
            generated.put(new DummyMove(i), new DummyState(gameState.getNumber() + i));
        }
        return generated;
    }

    @Override
    public Evaluation evaluateBoard(DummyState gameState) {
        return TwoPlayersBinaryEvaluation.DRAWING;
    }
}
