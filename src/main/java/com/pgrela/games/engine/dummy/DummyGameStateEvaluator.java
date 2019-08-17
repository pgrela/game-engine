package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.EvaluatorImpl;

public class DummyGameStateEvaluator implements EvaluatorImpl<DummyState> {
    @Override
    public Evaluation evaluateBoard(DummyState gameState) {
        return TwoPlayersBinaryEvaluation.DRAWING;
    }
}
