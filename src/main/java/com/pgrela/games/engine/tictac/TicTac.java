package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluator;
import com.pgrela.games.engine.api.Generator;
import com.pgrela.games.engine.api.Rules;

public class TicTac implements Rules {

    private TicTacGameStateEvaluator ticTacGameStateEvaluator = new TicTacGameStateEvaluator();
    private TicTacGameStateGenerator ticTacGameStateGenerator = new TicTacGameStateGenerator();

    @Override
    public Evaluator getEvaluator() {
        return ticTacGameStateEvaluator;
    }

    @Override
    public Generator getGenerator() {
        return ticTacGameStateGenerator;
    }

    @Override
    public Board initialBoard() {
        return new TicTacGameStateFactory().initialState();
    }
}
