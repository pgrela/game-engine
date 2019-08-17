package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluator;
import com.pgrela.games.engine.api.Generator;
import com.pgrela.games.engine.api.Rules;

public class Connect4 implements Rules {

    private Connect4GameStateGenerator connect4GameStateGenerator = new Connect4GameStateGenerator();
    private Connect4GameStateEvaluator connect4GameStateEvaluator = new Connect4GameStateEvaluator();

    @Override
    public Evaluator getEvaluator() {
        return connect4GameStateEvaluator;
    }

    @Override
    public Generator getGenerator() {
        return connect4GameStateGenerator;
    }

    @Override
    public Board initialBoard() {
        return new Connect4GameStateFactory().initialState();
    }
}
