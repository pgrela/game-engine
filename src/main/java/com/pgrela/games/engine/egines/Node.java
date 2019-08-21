package com.pgrela.games.engine.egines;

import com.google.common.collect.HashBiMap;
import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.api.Player;

import java.util.*;

public class Node {
    private HashBiMap<Move, Node> children;
    private PriorityQueue<Node> bestMoves;
    private Collection<Node> parents;

    private Board board;
    private Evaluation boardEvaluation;
    private Evaluation nodeEvaluation;

    private final Player currentlyMoving;
    private Move bestContinuation;
    private boolean expanded = false;

    private int depth;

    public Node(Board board, Evaluation boardEvaluation) {
        this.board = board;
        this.boardEvaluation = boardEvaluation;
        nodeEvaluation = boardEvaluation;
        children = HashBiMap.create();
        currentlyMoving = board.getNextPlayer();
        depth = 0;
        parents = new ArrayList<>();
        bestMoves = new PriorityQueue<>(Comparator.comparingDouble((Node node) -> node.nodeEvaluation.getForPlayer(currentlyMoving)).reversed());
    }

    public Node(Board board, Evaluation boardEvaluation, Node parent) {
        this(board, boardEvaluation);
        this.parents.add(parent);
        depth = parent.getDepth() + 1;
    }

    public Board getBoard() {
        return board;
    }

    void addParent(Node newParent) {
        parents.add(newParent);
    }

    public boolean wasExpanded() {
        return expanded;
    }

    public void markAsExpanded() {
        this.expanded = true;
    }

    private void recalculateEvaluation() {
        if (getChildren().isEmpty()) {
            setBestContinuation(null);
            nodeEvaluation = boardEvaluation;
            updateParents();
        } else {
            setBestContinuation(children.inverse().get(bestMoves.peek()));
            Evaluation newEvaluation = children.get(getBestContinuation()).getNodeEvaluation();
            if (!this.nodeEvaluation.equals(newEvaluation)) {
                this.nodeEvaluation = newEvaluation;
                updateParents();
            }
        }
    }

    private void update(Node updatedChild) {
        bestMoves.remove(updatedChild);
        bestMoves.add(updatedChild);
        recalculateEvaluation();
    }

    public Evaluation getNodeEvaluation() {
        return nodeEvaluation;
    }

    public Move getBestContinuation() {
        return bestContinuation;
    }

    private void setBestContinuation(Move bestContinuation) {
        this.bestContinuation = bestContinuation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node that = (Node) o;

        return board.equals(that.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    public void addChild(Move move, Node node) {
        children.put(move, node);
        bestMoves.add(node);
        if (children.size() == 1) {
            nodeEvaluation = node.getNodeEvaluation();
            setBestContinuation(move);
            updateParents();
        } else {
            update(node);
        }
    }

    private void updateParents() {
        parents.forEach(parent -> parent.update(this));
    }

    public Map<Move, Node> getChildren() {
        return children;
    }

    public Node follow(Move continuation) {
        return getChildren().get(continuation);
    }

    public Evaluation getStateEvaluation() {
        return boardEvaluation;
    }

    public Collection<Node> getParents() {
        return parents;
    }

    public int getDepth() {
        return depth;
    }

    public boolean hasAncestor(Node currentState) {
        if (this.equals(currentState)) {
            return true;
        }
        return parents.stream().anyMatch(p -> p.hasAncestor(currentState));
    }

    void disconnect() {
        getChildren().entrySet().forEach(c -> c.getValue().getParents().remove(this));
        parents.forEach(this::removeChild);
    }

    private void removeChild(Node c) {
        c.bestMoves.remove(this);
        c.children.inverse().remove(this);
        c.recalculateEvaluation();
    }

    public void getLostUnless(Map<Node, Boolean> hasCurrentAsAncestor, Node passedState) {
        if (!hasAncestor(hasCurrentAsAncestor)) {
            ArrayList<Node> children = new ArrayList<>(getChildren().values());
            this.children.clear();
            children.forEach(child -> child.removeParent(this));
            getParents().forEach(parent->parent.getLostUnless(hasCurrentAsAncestor, passedState));
        }
    }

    private void removeParent(Node node) {
        parents.remove(node);
    }

    private boolean hasAncestor(Map<Node, Boolean> visited) {
        Boolean cachedResult = visited.get(this);
        if (cachedResult != null) {
            return cachedResult;
        }
        for (Node parent : getParents()) {
            if (parent.hasAncestor(visited)) {
                visited.put(this, true);
                return true;
            }
        }
        visited.put(this, false);
        return false;
    }
}
