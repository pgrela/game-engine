package com.pgrela.games.engine.api;

public interface EvaluatorImpl<GAME_STATE extends Board> extends Evaluator {
    Evaluation evaluateBoard(GAME_STATE gameState);

    @Override
    default Evaluation evaluate(Board board) {
        return evaluateBoard((GAME_STATE) board);
    }
}
