package com.pgrela.games.engine;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class GameTreeNode {
    private Collection<GameTreeNode> children;
    private GameState state;
    private PriorityQueue<GameTreeNode> leafs;
    private Map<GameState, GameTreeNode> nodes;
    private Collection<GameTreeNode> parents = new ArrayList<>();
    private GameTreeNodeEvaluation evaluation;
    private Player currentlyMoving;
    private GameTreeNode bestContinuation;
    private boolean expanded = false;

    public GameTreeNode(GameState state, PriorityQueue<GameTreeNode> leafs, Map<GameState, GameTreeNode> nodes) {
        this.leafs = leafs;
        this.nodes = nodes;
        this.children = Lists.newArrayList();
        this.state = state;
        evaluation = new GameTreeNodeEvaluation(state.getEvaluation());
        currentlyMoving = state.getNextPlayer();
    }

    public GameTreeNode(GameState state, PriorityQueue<GameTreeNode> leafs, Map<GameState, GameTreeNode> nodes, GameTreeNode parent) {
        this(state, leafs, nodes);
        this.parents.add(parent);
    }

    public Collection<GameTreeNode> getChildren() {
        return children;
    }

    public GameState getState() {
        return state;
    }

    public void expand() {
        for (GameState nextState : state.next()) {
            if (nodes.containsKey(nextState)) {
                GameTreeNode node = nodes.get(nextState);
                node.addParent(this);
                children.add(node);
            } else {
                GameTreeNode newNode = new GameTreeNode(nextState, leafs, nodes, this);
                nodes.put(nextState, newNode);
                children.add(newNode);
            }
        }
        if (children.isEmpty()) {
            return;
        }
        updateEvaluation();
        leafs.addAll(children.stream()
                .filter(node -> !node.state.getEvaluation().isDecisive())
                .filter(node -> !leafs.contains(node))
                .filter(node -> !node.wasExpanded())
                .collect(Collectors.toList()));
        expanded = true;
    }

    private void addParent(GameTreeNode gameTreeNode) {
        parents.add(gameTreeNode);
    }

    public boolean wasExpanded() {
        return expanded;
    }

    private void updateEvaluation() {
        bestContinuation = children.stream().max(Comparator.comparingDouble(a -> a.getEvaluation().getForPlayer(currentlyMoving))).get();
        evaluation.update(bestContinuation.getEvaluation());
        parents.forEach(parent->parent.update(this));
    }

    private void update(GameTreeNode updatedChild) {
        updateEvaluation();
    }

    public GameTreeNodeEvaluation getEvaluation() {
        return evaluation;
    }

    public GameTreeNode getBestContinuation() {
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
}
