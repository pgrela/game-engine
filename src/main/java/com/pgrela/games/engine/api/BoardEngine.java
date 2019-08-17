package com.pgrela.games.engine.api;

import java.util.List;

public class BoardEngine implements Engine {

    protected Board board;

    @Override
    public void initialize(Board board) {

        this.board = board;
    }

    @Override
    public Move getBestMove() {
        return null;
    }

    @Override
    public List<Move> getBestMovesSequence() {
        return null;
    }

    @Override
    public void move(Move move) {

    }

    @Override
    public Evaluation getEvaluation() {
        return null;
    }

    @Override
    public Board getCurrentBoard() {
        return board;
    }
}
