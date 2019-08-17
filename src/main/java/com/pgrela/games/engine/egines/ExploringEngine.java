package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.*;
import com.pgrela.games.engine.dummy.DummyState;
import com.pgrela.games.engine.dummy.TwoPlayersEvaluation;

import java.util.*;

public class ExploringEngine extends BoardEngine {
    private int depth;
    private Deque<DummyGameTreeNode> leafs;
    //private Map<Board, DummyGameTreeNode> nodes = new HashMap<>();
    private Evaluator evaluator;
    private Generator generator;
    private DummyGameTreeNode current;

    public ExploringEngine(Evaluator evaluator, Generator generator, int depth) {
        this.evaluator = evaluator;
        this.generator = generator;
        this.depth = depth;
    }

    @Override
    public void initialize(Board board) {
        super.initialize(board);
        leafs = new ArrayDeque<>();
        current = new DummyGameTreeNode(this.board);
        current.updateEvaluation(evaluator.evaluate(board));
        leafs.add(current);
    }

    public void start() {
        while (!leafs.isEmpty() && leafs.peek().getDepth() - current.getDepth() <= depth) {
            follow(leafs.poll());
        }
        System.out.println("Played");
        //System.out.println("Analyzed " + nodes.size() + " nodes.");
        System.out.println("Created " + DummyState.c + " states.");
    }

    private void follow(DummyGameTreeNode anExpandable) {
        if(!anExpandable.hasAncestor(current)){
            return;
        }
        expand(anExpandable);
    }


    public void expand(DummyGameTreeNode leaf) {
        Map<Move, Board> next = generator.next(leaf.getBoard());
        Move bestContinuation = null;
        double bestMove = -TwoPlayersEvaluation.MAX;
        for (Map.Entry<Move, Board> nextState : next.entrySet()) {
            Move move = nextState.getKey();
            Board board = nextState.getValue();
            DummyGameTreeNode newNode = new DummyGameTreeNode(board, leaf);
            //nodes.put(board, newNode);
            newNode.updateEvaluation(evaluator.evaluate(board));
            leaf.addChild(move, newNode);
            double forPlayer = newNode.getEvaluation().getForPlayer(leaf.getBoard().getNextPlayer());
            if (forPlayer > bestMove) {
                bestMove = forPlayer;
                bestContinuation = move;
            }
        }
        if (bestContinuation != null)
            leaf.updateEvaluation(bestContinuation);

        leafs.addAll(leaf.getChildren().values());
    }

    @Override
    public Evaluation getEvaluation() {
        return current.getEvaluation();
    }


    @Override
    public Move getBestMove() {
        return current.getBestMove();
    }

    @Override
    public List<Move> getBestMovesSequence() {
        List<Move> moves = new ArrayList<>();
        Move continuation;
        DummyGameTreeNode c = current;
        moves.add(current.getBestMove());
        do {
            continuation = c.getBestMove();
            c = c.follow(continuation);
            if (c == null) break;
            moves.add(continuation);
            System.out.println(c.getBoard() + " " + c.getEvaluation() + " by " + continuation);
        } while (!c.getEvaluation().isDecisive());
        return moves;
    }

    @Override
    public void move(Move move) {
        current = current.follow(move);
        current.parent = null;
    }

    @Override
    public Board getCurrentBoard() {
        return current.getBoard();
    }
}
