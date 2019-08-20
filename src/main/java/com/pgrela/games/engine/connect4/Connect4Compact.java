package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluator;
import com.pgrela.games.engine.api.Generator;
import com.pgrela.games.engine.api.Rules;

public class Connect4Compact implements Rules {

    private Connect4CompactBoardGenerator generator = new Connect4CompactBoardGenerator();
    private Connect4CompactBoardEvaluator evaluator = new Connect4CompactBoardEvaluator();

    @Override
    public Evaluator getEvaluator() {
        return evaluator;
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public Board initialBoard() {
        return new Connect4CompactBoardFactory().initialState();
    }
}
