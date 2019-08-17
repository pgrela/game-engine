package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.api.Player;

import java.util.HashMap;
import java.util.Map;

public class DummyGameTreeNode {
    private Map<Move, DummyGameTreeNode> children;
    DummyGameTreeNode parent;
    private Board board;
    private Evaluation evaluation;
    private Move bestMove;
    int depth;

    public DummyGameTreeNode(Board board) {
        this.children = new HashMap<>();
        this.board = board;
        depth = 0;
    }

    public DummyGameTreeNode(Board board, DummyGameTreeNode parent) {
        this(board);
        this.parent = parent;
        depth = parent.getDepth() + 1;
    }

    public Board getBoard() {
        return board;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DummyGameTreeNode that = (DummyGameTreeNode) o;

        return board.equals(that.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    public void addChild(Move move, DummyGameTreeNode node) {
        children.put(move, node);
    }

    public Map<Move, DummyGameTreeNode> getChildren() {
        return children;
    }

    public DummyGameTreeNode follow(Move continuation) {
        return children.get(continuation);
    }

    public int getDepth() {
        return depth;
    }

    private void update(DummyGameTreeNode updatedChild) {
        Player currentlyMoving = board.getNextPlayer();
        if (updatedChild.evaluation.getForPlayer(currentlyMoving) > evaluation.getForPlayer(currentlyMoving)) {
            evaluation = updatedChild.evaluation;
            if (parent != null) parent.update(this);
            for (Map.Entry<Move, DummyGameTreeNode> entry : children.entrySet()) {
                if (entry.getValue().equals(updatedChild)) {
                    setBestMove(entry.getKey());
                    break;
                }
            }
        }
    }

    public void updateEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void setBestMove(Move bestMove) {
        this.bestMove = bestMove;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void updateEvaluation(Move bestContinuation) {
        this.bestMove = bestContinuation;
        DummyGameTreeNode child = children.get(bestContinuation);
        this.updateEvaluation(child.getEvaluation());
        if (parent != null)
            parent.update(child);
    }

    public Move getBestMove() {
        return bestMove;
    }


    public boolean hasAncestor(DummyGameTreeNode ancestor) {
        if (this.equals(ancestor)) {
            return true;
        }
        if (parent != null) {
            return parent.hasAncestor(ancestor);
        }
        return false;
    }
}
