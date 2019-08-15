package com.pgrela.games.engine;

import java.util.*;

public class GameTreeNode<MOVE extends Move, GAME_STATE extends GameState> {
    private Map<Move, GameTreeNode<MOVE, GAME_STATE>> children;
    private Collection<GameTreeNode> parents = new ArrayList<>();
    private GAME_STATE state;
    private GameTreeNodeEvaluation nodeEvaluation;
    private Evaluation evaluation;

    private Player currentlyMoving;
    private Move bestContinuation;
    private boolean expanded = false;
    private Engine<MOVE, GAME_STATE> engine;

    public GameTreeNode(GAME_STATE state, Engine<MOVE, GAME_STATE> engine) {
        this.engine = engine;
        this.children = new HashMap<>();
        this.state = state;
        evaluation = engine.evaluate(state);
        nodeEvaluation = new GameTreeNodeEvaluation(evaluation);
        currentlyMoving = state.getNextPlayer();
    }

    public GameTreeNode(GAME_STATE state, GameTreeNode parent, Engine<MOVE, GAME_STATE> engine) {
        this(state, engine);
        this.parents.add(parent);
    }

    public GAME_STATE getState() {
        return state;
    }


    void addParent(GameTreeNode gameTreeNode) {
        parents.add(gameTreeNode);
    }

    public boolean wasExpanded() {
        return expanded;
    }

    public void markAsExpanded() {
        this.expanded = true;
    }

    void updateEvaluation() {
        bestContinuation = children.entrySet().stream().max(Comparator.comparingDouble(a -> a.getValue().getNodeEvaluation().getForPlayer(currentlyMoving))).get().getKey();
        nodeEvaluation.update(children.get(bestContinuation).getNodeEvaluation());
        parents.forEach(parent -> parent.update(this));
    }

    private void update(GameTreeNode updatedChild) {
        updateEvaluation();
    }

    public GameTreeNodeEvaluation getNodeEvaluation() {
        return nodeEvaluation;
    }

    public Move getBestContinuation() {
        return bestContinuation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameTreeNode that = (GameTreeNode) o;

        return state.equals(that.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    public void addChild(MOVE move, GameTreeNode<MOVE, GAME_STATE> node) {
        children.put(move, node);
    }

    public Map<Move, GameTreeNode<MOVE, GAME_STATE>> getChildren() {
        return children;
    }

    public GameTreeNode<MOVE, GAME_STATE> follow(Move continuation) {
        return children.get(continuation);
    }

    public Evaluation getStateEvaluation() {
        return evaluation;
    }
}
