package com.pgrela.games.engine.api;

import java.util.List;

public interface Engine {
    void initialize(Board board);
    default void start(){};
    default void pause(){};
    Move getBestMove();
    List<Move> getBestMovesSequence();
    void move(Move move);
    Evaluation getEvaluation();
    Board getCurrentBoard();
}
