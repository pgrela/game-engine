package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluator;
import com.pgrela.games.engine.api.Generator;
import com.pgrela.games.engine.api.Rules;

public class Dummy implements Rules {
    @Override
    public Evaluator getEvaluator() {
        return new DummyGameStateEvaluator();
    }

    @Override
    public Generator getGenerator() {
        return new DummyGameStateGenerator();
    }

    @Override
    public Board initialBoard() {
        return new DummyGameStateFactory().initialState();
    }

}
