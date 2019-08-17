package com.pgrela.games.engine.api;

public interface Rules {

    Evaluator getEvaluator();

    Generator getGenerator();

    Board initialBoard();
}
